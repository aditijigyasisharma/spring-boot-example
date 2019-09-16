package com.ajs.demo.banking.service;

import com.ajs.demo.banking.BasicTestConfig;
import com.ajs.demo.banking.model.Account;
import com.ajs.demo.banking.model.AccountTransactions;
import com.ajs.demo.banking.model.TransactionInfo;
import com.ajs.demo.banking.repository.AccountRepository;
import com.ajs.demo.banking.repository.AccountTransactionsRepository;
import com.ajs.demo.banking.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class SavingsAccountServiceTest extends BasicTestConfig {

	@Autowired
	private SavingsAccountService savingsAccountService;

	@MockBean
	AccountRepository accountRepository;

	@MockBean
	AccountTransactionsRepository accountTransactionsRepository;

	@MockBean
	UserRepository userRepository;

	List<AccountTransactions> accountTransactionsList = new ArrayList<>();

	@Before
	public void setUp() {
		Account account = initAccount();
		AccountTransactions accountTransactions = initAccountTransaction();
		accountTransactionsList.add(accountTransactions);
		AccountTransactions accountTransactions2 = initAccountTransaction();
		accountTransactions2.setId(ACCOUNT_TRANSACTION_ID_NEW);
		accountTransactions2.setAccountId(ACCOUNT_ID_NEW);
		accountTransactionsList.add(accountTransactions2);
		Mockito.when(accountRepository.findById(Mockito.any(Long.class)))
				.thenReturn(java.util.Optional.ofNullable(account));
		Mockito.when(accountTransactionsRepository.findAccountTransactionsByAccountId(ACCOUNT_ID))
				.thenReturn(accountTransactionsList);
		Mockito.when(accountRepository.save(Mockito.any(Account.class)))
				.thenReturn(account);
		Mockito.when(accountTransactionsRepository.save(Mockito.any(AccountTransactions.class)))
				.thenReturn(accountTransactions);

	}

	@Test
	public void testGetAccountDetails() {
		Account account = savingsAccountService.getAccountDetails(ACCOUNT_ID);
		assertNotNull(account);
		assertEquals(account.getAccountId(), ACCOUNT_ID);
	}

	@Test
	public void testGetAccountStatement() {
		List<AccountTransactions> statement = savingsAccountService.getStatement(ACCOUNT_ID);
		assertNotNull(statement);
		assertEquals(statement.size(), 2);
		long actualAccountId = statement.get(0).getAccountId();
		assertEquals(actualAccountId, ACCOUNT_ID.longValue());
		actualAccountId = statement.get(1).getAccountId();
		assertEquals(actualAccountId, ACCOUNT_ID_NEW.longValue());

	}

	@Test
	public void testTransact() {
		TransactionInfo transactionInfo = initTransactionInfo();
		List<AccountTransactions> transactions = savingsAccountService.transact(transactionInfo);
		assertEquals(transactions.size(), 2);
	}
}
