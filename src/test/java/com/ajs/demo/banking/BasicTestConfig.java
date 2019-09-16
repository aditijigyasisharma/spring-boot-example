package com.ajs.demo.banking;

import com.ajs.demo.banking.config.RepositoryConfig;
import com.ajs.demo.banking.model.Account;
import com.ajs.demo.banking.model.AccountTransactions;
import com.ajs.demo.banking.model.TransactionInfo;
import com.ajs.demo.banking.model.User;
import com.ajs.demo.banking.repository.AccountRepository;
import com.ajs.demo.banking.repository.AccountTransactionsRepository;
import com.ajs.demo.banking.repository.UserRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

@ContextConfiguration(classes = RepositoryConfig.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankingApplication.class)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public abstract class BasicTestConfig {
	public static final Long ACCOUNT_ID = 109L;
	public static final Long ACCOUNT_ID_NEW = 108L;
	public static final Integer BALANCE = 1000;
	public static final Integer BALANCE_NEW = 1500;
	public static final Long USER_ID = 111L;
	public static final Long NEW_USER_ID = 333L;
	public static final Long ACCOUNT_TRANSACTION_ID = 1L;
	public static final Long ACCOUNT_TRANSACTION_ID_NEW = 2L;
	public static final String MESSAGE = "Initial balance";
	public static final Integer DEBIT_AMOUNT = 500;
	public static final Long DELETE_ACCOUNT_ID = 101L;
	public static final Long FROM_ACCOUNT = 101L;
	public static final Long TO_ACCOUNT = 102L;


	@Autowired
	protected AccountRepository accountRepository;

	@Autowired
	protected AccountTransactionsRepository accountTransactionsRepository;

	@Autowired
	UserRepository userRepository;

	protected <T> T convertFromJson(String json, Class<T> type)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, type);
	}

	protected String convertToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}


	public Account initAccount() {
		Account account = new Account();
		account.setAccountId(ACCOUNT_ID);
		account.setBalance(BALANCE);
		account.setUserId(USER_ID);
		return account;
	}

	public User initUser() {
		User user = new User();
		user.setUserId(NEW_USER_ID);
		user.setAddress("Abc");
		user.setPassword("ghn");
		user.setName("TestUser");
		return user;

	}

	public TransactionInfo initTransactionInfo() {
		TransactionInfo transactionInfo = new TransactionInfo();
		transactionInfo.setAmount(DEBIT_AMOUNT);
		transactionInfo.setFromAccountId(FROM_ACCOUNT);
		transactionInfo.setToAccountId(TO_ACCOUNT);
		return transactionInfo;
	}

	public AccountTransactions initAccountTransaction() {
		AccountTransactions accountTransactions = new AccountTransactions();
		accountTransactions.setId(ACCOUNT_TRANSACTION_ID);
		accountTransactions.setBalance(BALANCE);
		accountTransactions.setMessage(MESSAGE);
		accountTransactions.setUpdatedBy(USER_ID);
		accountTransactions.setAccountId(ACCOUNT_ID);
		Instant instant = Instant.now();
		long timeStampSeconds = instant.getEpochSecond();
		accountTransactions.setUpdatedOn(timeStampSeconds);
		return accountTransactions;
	}


	public Account saveAccount(Account account) {
		return accountRepository.save(account);
	}

	public void deleteAccount(Long accountId) {
		List<AccountTransactions> accountTransactions = accountTransactionsRepository.findAccountTransactionsByAccountId(accountId);
		accountTransactionsRepository.deleteAll(accountTransactions);
		accountRepository.deleteById(accountId);
	}

}
