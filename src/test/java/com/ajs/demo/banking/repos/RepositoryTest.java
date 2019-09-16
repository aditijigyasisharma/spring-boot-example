package com.ajs.demo.banking.repos;

import com.ajs.demo.banking.BasicTestConfig;
import com.ajs.demo.banking.exceptions.SavingsAccountNotFoundException;
import com.ajs.demo.banking.model.Account;
import com.ajs.demo.banking.model.User;
import com.ajs.demo.banking.repository.AccountRepository;
import com.ajs.demo.banking.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RepositoryTest extends BasicTestConfig {

	@Autowired
	protected AccountRepository accountRepository;

	@Autowired
	UserRepository userRepository;

	private Account account;
	private User user;

	@Test
	public void testCreatingAccount() {
		user = initUser();
		account = initAccount();
		Account newAccount = saveAccount(account);
		assertNotNull(newAccount.getAccountId());
		assertEquals(BALANCE, newAccount.getBalance());
	}

	@Test
	public void testUpdatingAccount() {
		account = initAccount();
		account.setBalance(BALANCE_NEW);
		Account updatedAccount = saveAccount(account);
		assertNotNull(updatedAccount.getAccountId());
		assertNotEquals(BALANCE, updatedAccount.getBalance());
		assertEquals(updatedAccount.getBalance(), account.getBalance());
	}

	@Test
	public void testDeletingAccount() throws SavingsAccountNotFoundException {
		deleteAccount(DELETE_ACCOUNT_ID);
		assertFalse(accountRepository.findById(ACCOUNT_ID).isPresent());
	}
}
