package com.pos.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pos.model.Customer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


/**
 * Created by rrampall on 21/01/18.
 */
public interface CustomersRepository extends CrudRepository<Customer, Long> {

  @Query("select mobileNumber from Customer c where c.mobileNumber like ':searchPattern'")
  List<Long> findMobileNumberBySearchPattern(@Param("searchPattern") String searchPattern);

}
