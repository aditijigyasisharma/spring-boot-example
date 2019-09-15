package com.ajs.demo.banking;

import com.ajs.demo.banking.config.BankingDemoConfig;
import com.ajs.demo.banking.config.BankingDemoConfigTest;
import com.ajs.demo.banking.repository.AccountRepository;
import com.ajs.demo.banking.repository.AccountTransactionsRepository;
import com.ajs.demo.banking.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.PostConstruct;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BankingDemoConfigTest.class)
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {


	@Autowired
	protected AccountRepository accountRepository;

	@Autowired
	protected AccountTransactionsRepository accountTransactionsRepository;

	@Autowired
	protected UserRepository userRepository;

	protected ObjectMapper objectMapper;

	@PostConstruct
	public void init() {
		objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		objectMapper.registerModule(module);
	}




}
