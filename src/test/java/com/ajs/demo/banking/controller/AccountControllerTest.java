package com.ajs.demo.banking.controller;


import com.ajs.demo.banking.AbstractIntegrationTest;
import com.ajs.demo.banking.model.Account;
import com.ajs.demo.banking.service.SavingsAccountService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class AccountControllerTest  extends AbstractIntegrationTest {

	@MockBean
	private SavingsAccountsController savingsAccountsController;

	private Long ACCOUNT_ID = 103L;
	private Integer BALANCE = 1000;
	private  Long USER_ID = 101L;
	private MockMvc mockMvc = null;

	@Mock
	private SavingsAccountService savingsAccountService;



	@BeforeEach
	public void setUp() {
		if (this.mockMvc == null) {
			this.mockMvc = MockMvcBuilders.standaloneSetup(new SavingsAccountsController(savingsAccountService)).build();
		}
	}
	@Test
	public void getAccountDetails() throws Exception {
		Account account = new Account();
		account.setAccountId(ACCOUNT_ID);
		account.setBalance(BALANCE);
		account.setUserId(USER_ID);

		MvcResult mvcResult = this.mockMvc.perform(get("/accounts/savings/" +ACCOUNT_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(account))).andReturn();

		assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());

		Account accountNew = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<Account>() {
		});
		assertEquals(accountNew.getAccountId(),account.getAccountId() );
	}


}
