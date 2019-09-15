package com.ajs.demo.banking.config;

import com.ajs.demo.banking.repository.AccountRepository;
import com.ajs.demo.banking.repository.AccountTransactionsRepository;
import com.ajs.demo.banking.repository.UserRepository;
import com.ajs.demo.banking.service.SavingsAccountService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.ajs.demo.banking"})
@EnableJpaRepositories(basePackageClasses = {AccountRepository.class, UserRepository.class, AccountTransactionsRepository.class})
@Import({SavingsAccountService.class})
@EnableTransactionManagement
public class BankingDemoConfigTest {
}

