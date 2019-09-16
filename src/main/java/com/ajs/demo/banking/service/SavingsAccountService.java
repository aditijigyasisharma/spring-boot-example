package com.ajs.demo.banking.service;


import com.ajs.demo.banking.exceptions.LowBalanceException;
import com.ajs.demo.banking.exceptions.SavingsAccountException;
import com.ajs.demo.banking.exceptions.SavingsAccountNotFoundException;
import com.ajs.demo.banking.model.Account;
import com.ajs.demo.banking.model.AccountTransactions;
import com.ajs.demo.banking.model.TransactionInfo;
import com.ajs.demo.banking.model.User;
import com.ajs.demo.banking.repository.AccountRepository;
import com.ajs.demo.banking.repository.AccountTransactionsRepository;
import com.ajs.demo.banking.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
public class SavingsAccountService extends AccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SavingsAccountService.class);

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	AccountTransactionsRepository accountTransactionsRepository;

	@Autowired
	UserRepository userRepository;

	public Account getAccountDetails(Long accountId) {
		Optional<Account> accountDetails = accountRepository.findById(accountId);
		if (accountDetails.isPresent()) {
			return accountDetails.get();
		} else {
			LOGGER.error("Account not found");
			throw new SavingsAccountNotFoundException("No Account  exist for given id: " + accountId);
		}
	}

	@Override
	public List<AccountTransactions> getStatement(Long accountId) {
		List<AccountTransactions> accountTransactions = accountTransactionsRepository.findAccountTransactionsByAccountId(accountId);
		if (accountTransactions != null) {
			return accountTransactions;
		} else {
			LOGGER.error("Account statement not found");
			throw new SavingsAccountNotFoundException("No Account statement exist for given id: " + accountId);
		}
	}

	public Account getAccount(Long accountId) {
		Optional<Account> optionalAccount = accountRepository.findById(accountId);
		Account account;
		if (optionalAccount.isPresent()) {
			account = optionalAccount.get();
		} else {
			throw new SavingsAccountNotFoundException("Account not present with accountId" + accountId);
		}
		return account;
	}

	@Override
	public List<AccountTransactions> transact(TransactionInfo transactionInfo) {
		Account fromAccount = getAccount(transactionInfo.getFromAccountId());
		Account toAccount = getAccount(transactionInfo.getToAccountId());
		List<AccountTransactions> accountTransactions = new ArrayList<>();
		Integer amount = transactionInfo.getAmount();
		AtomicInteger fromBalance = new AtomicInteger(fromAccount.getBalance());
		AtomicInteger toBalance = new AtomicInteger(toAccount.getBalance());

		LOGGER.info("Transaction starting for::" + fromAccount.getAccountId() + " to account " + toAccount.getAccountId() + " for Rs. " + amount);

		try {
			fromBalance.getAndSet(fromBalance.intValue() - amount);
			if (fromBalance.intValue() <= 0) {
				Optional<User> user = userRepository.findById(fromAccount.getUserId());
				String username = "";
				if (user.isPresent()) {
					username = user.get().getName();
				}
				String message = "Balance is low  for  '" + username + "' with account id " + fromAccount.getAccountId() + " cannot complete transaction!";
				throw new LowBalanceException(message);
			} else {
				toBalance.getAndAdd(amount.intValue());
				LOGGER.info("toBalance::" + toBalance.intValue());
				toAccount.setBalance(toBalance.intValue());
				fromAccount.setBalance(fromBalance.intValue());
			}

			// Save two accounts
			accountRepository.save(fromAccount);
			accountRepository.save(toAccount);

			// Create Audit Trail
			String transactionMsg = "Account " + toAccount.getAccountId() + " credited with " + amount;
			String fromTransactionMsg = "Account " + fromAccount.getAccountId() + " debited with " + amount;
			AccountTransactions toAccountTransactions = this.createAccountTransactionObject(toAccount, toBalance.intValue(), transactionMsg);
			AccountTransactions fromAccountTransactions = this.createAccountTransactionObject(fromAccount, fromBalance.intValue(), fromTransactionMsg);
			accountTransactions.add(toAccountTransactions);
			accountTransactions.add(fromAccountTransactions);

			accountTransactionsRepository.save(toAccountTransactions);
			accountTransactionsRepository.save(fromAccountTransactions);

			return accountTransactions;
		} catch (Exception e) {
			throw new SavingsAccountException("Exception occured in transaction");
		}
	}

	public AccountTransactions createAccountTransactionObject(Account account, Integer balance, String message) {
		AccountTransactions accountTransactionsObj = new AccountTransactions();
		Instant instant = Instant.now();
		long timeStampSeconds = instant.getEpochSecond();
		accountTransactionsObj.setAccountId(account.getAccountId());
		accountTransactionsObj.setBalance(balance);
		accountTransactionsObj.setMessage(message);
		accountTransactionsObj.setUpdatedBy(account.getUserId());
		accountTransactionsObj.setUpdatedOn(timeStampSeconds);
		return accountTransactionsObj;
	}

	@Override
	public Account create(Account account) {
		return accountRepository.save(account);

	}

	@Override
	public void delete(Long accountId) {
		accountRepository.deleteById(accountId);
	}
}
