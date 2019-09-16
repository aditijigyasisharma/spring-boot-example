package com.ajs.demo.banking.controller;


import com.ajs.demo.banking.model.Account;
import com.ajs.demo.banking.model.AccountTransactions;
import com.ajs.demo.banking.model.TransactionInfo;
import com.ajs.demo.banking.service.SavingsAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value={"/accounts/savings/"})
public class SavingsAccountsController {

    @Autowired
    private SavingsAccountService savingsAccountService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SavingsAccountsController.class);

    @GetMapping(path="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getAccountDetails(@PathVariable Long id) {
        LOGGER.info("Getting details for accountId::" + id);
        Account accountDetails= savingsAccountService.getAccountDetails(id);
        if (accountDetails == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accountDetails, HttpStatus.OK);
    }

    @PutMapping(path="create",  produces = "application/json",headers="Accept=application/json")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        LOGGER.info("Creating new account::" + account);
        Account accountCreated =  savingsAccountService.create(account);
        return new ResponseEntity<>(accountCreated, HttpStatus.CREATED);

    }

    @DeleteMapping(path="{id}")
    public ResponseEntity<Account> deleteAccount(@PathVariable Long id) {
        LOGGER.info("Deleting account::" + id);
        savingsAccountService.delete(id);
        return  new ResponseEntity("Deleted", HttpStatus.OK);
    }

    @GetMapping(path="{id}/statement", produces = "application/json")
    public ResponseEntity<List<AccountTransactions>> getAccountStatement(@PathVariable Long id) {
        LOGGER.info("Getting account statement for::" + id);
        List<AccountTransactions> accountStatement = savingsAccountService.getStatement(id);
        LOGGER.info("Getting account statement accountStatement::" + accountStatement);
        return  new ResponseEntity(accountStatement, HttpStatus.OK);
    }

    @PostMapping(path="transact", produces = "application/json")
    public ResponseEntity<List<AccountTransactions>> transact(@RequestBody TransactionInfo transactionInfo) {
        LOGGER.info("Perform transaction  for::" + transactionInfo);
        List<AccountTransactions> transactionResult = savingsAccountService.transact(transactionInfo);
        return  new ResponseEntity(transactionResult, HttpStatus.OK);
    }

}
