package com.ajs.demo.banking.service;

import com.ajs.demo.banking.model.Account;
import com.ajs.demo.banking.model.AccountTransactions;
import com.ajs.demo.banking.model.TransactionInfo;

import java.util.List;

public abstract class AccountService {
    public abstract List<AccountTransactions> getStatement(Long accountId);
    public abstract List<AccountTransactions> transact(TransactionInfo creditInfo);
    public abstract Account create(Account account);
    public abstract void delete(Long accountId);
}
