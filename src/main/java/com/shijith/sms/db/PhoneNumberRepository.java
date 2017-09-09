package com.shijith.sms.db;

import com.shijith.sms.bean.PhoneNumber;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhoneNumberRepository extends CrudRepository<PhoneNumber, Integer> {

    List<PhoneNumber> findPhoneNumberByAccount_id(@Param("account_id") Integer account_id);
}
