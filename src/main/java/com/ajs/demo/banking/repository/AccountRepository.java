package com.ajs.demo.banking.repository;

import com.ajs.demo.banking.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository  extends CrudRepository<Account, Long> {


}
