package com.ajs.demo.banking.repos;

import com.ajs.demo.banking.AbstractIntegrationTest;
import com.ajs.demo.banking.config.BankingDemoConfigTest;
import com.ajs.demo.banking.exceptions.SavingsAccountNotFoundException;
import com.ajs.demo.banking.model.Account;
import com.ajs.demo.banking.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class AccountRepositoryTest   extends AbstractIntegrationTest {

	private Account account;
	private Long ACCOUNT_ID = 103L;
	private Integer BALANCE = 1000;
	private  Long USER_ID = 101L;
	@Autowired
	protected AccountRepository accountRepository;


	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreatingAccount() {
		account.setAccountId(ACCOUNT_ID);
		account.setBalance(BALANCE);
		account.setUserId(USER_ID);
		accountRepository.save(account);

		this.account = accountRepository.findById(ACCOUNT_ID).get();
		assertNotNull(account.getAccountId());
		assertEquals(BALANCE, account.getBalance());

	}

	@Test
	public void testUpdatingAccount() {
		this.account.setBalance(1500);
		accountRepository.save(this.account);

		Account updatedAccount = accountRepository.findById(ACCOUNT_ID).get();

		assertNotNull(updatedAccount.getAccountId());
		assertNotEquals(BALANCE, updatedAccount.getBalance());
		assertEquals(updatedAccount.getBalance(),updatedAccount);

	}

	@Test
	public void testDeletingAccount() throws  SavingsAccountNotFoundException {
		accountRepository.deleteById(ACCOUNT_ID);
		Assertions.assertThrows(SavingsAccountNotFoundException.class, () -> accountRepository.findById(ACCOUNT_ID));
	}
}
