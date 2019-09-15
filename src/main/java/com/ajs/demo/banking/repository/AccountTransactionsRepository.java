package com.ajs.demo.banking.repository;

import com.ajs.demo.banking.model.AccountTransactions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountTransactionsRepository  extends CrudRepository<AccountTransactions, Long> {

	public List<AccountTransactions> findAccountTransactionsByAccountId(Long accountId);
}
