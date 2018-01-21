package com.pos.repository;

import com.pos.model.Customer;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by rrampall on 21/01/18.
 */
public interface CustomersRepository extends CrudRepository<Customer, Long> {


}
