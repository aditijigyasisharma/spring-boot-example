package com.ajs.demo.banking.controller;


import com.ajs.demo.banking.BasicTestConfig;
import com.ajs.demo.banking.model.Account;
import com.ajs.demo.banking.model.AccountTransactions;
import com.ajs.demo.banking.model.TransactionInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class AccountControllerTest extends BasicTestConfig {

	@MockBean
	private SavingsAccountsController savingsAccountsController;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetAccountDetails() throws Exception {
		Account account = initAccount();

		ResponseEntity<Account> responseEntity = new ResponseEntity<>(account, HttpStatus.OK);
		given(savingsAccountsController.getAccountDetails(ACCOUNT_ID)).willReturn(responseEntity);

		this.mockMvc.perform(get("http://localhost:8080/accounts/savings/" + ACCOUNT_ID)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("balance", is(account.getBalance())));

	}

	@Test
	public void testGetAccountStatement() throws Exception {
		AccountTransactions accountTransactions = initAccountTransaction();
		List<AccountTransactions> accountTransactionsList = new ArrayList<>();
		accountTransactionsList.add(accountTransactions);

		ResponseEntity<List<AccountTransactions>> responseEntity = new ResponseEntity<>(accountTransactionsList, HttpStatus.OK);
		given(savingsAccountsController.getAccountStatement(ACCOUNT_ID)).willReturn(responseEntity);

		this.mockMvc.perform(get("http://localhost:8080/accounts/savings/" + ACCOUNT_ID + "/statement")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void testCreateAccount() throws Exception {
		Account account = initAccount();
		ResponseEntity<Account> responseEntity = new ResponseEntity<>(account, HttpStatus.CREATED);
		given(savingsAccountsController.createAccount(account)).willReturn(responseEntity);
		String inputJson = this.convertToJson(account);
		this.mockMvc.perform(put("http://localhost:8080/accounts/savings/create").header("Accept", "application/json").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andExpect(status().isOk());

	}

	@Test
	public void testTransactAccount() throws Exception {
		AccountTransactions toAccountTransactions = initAccountTransaction();
		List<AccountTransactions> accountTransactionsList = new ArrayList<>();
		AccountTransactions fromAccountTransactions = initAccountTransaction();

		fromAccountTransactions.setBalance(500);
		toAccountTransactions.setBalance(1000);

		accountTransactionsList.add(toAccountTransactions);
		accountTransactionsList.add(fromAccountTransactions);

		TransactionInfo transactionInfo = initTransactionInfo();
		ResponseEntity<List<AccountTransactions>> responseEntity = new ResponseEntity<>(accountTransactionsList, HttpStatus.OK);
		given(savingsAccountsController.transact(transactionInfo)).willReturn(responseEntity);
		String inputJson = this.convertToJson(transactionInfo);
		this.mockMvc.perform(post("http://localhost:8080/accounts/savings/transact").header("Accept", "application/json").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andExpect(status().isOk());
	}
}
