package com.shijith.sms.db;

import com.shijith.sms.bean.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Integer> {

    List<Account> findAccountByUsername(@Param("userName") String userName);

}
