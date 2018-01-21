package com.pos.service;

import com.pos.model.Customer;
import com.pos.repository.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by rrampall on 18/12/17.
 */
public class CustomerService {

    @Autowired
    private CustomersRepository customersRepository;

    public Customer fetchCustomer(Long mobileNumber) {
       return customersRepository.findOne(mobileNumber);
    }


}
