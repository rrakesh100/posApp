package com.pos.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pos.model.Customer;
import com.pos.pojos.XCustomer;
import com.pos.repository.CustomersRepository;
import sun.awt.X11.XChoicePeer;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by rrampall on 18/12/17.
 */
public class CustomerService {

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private Mapper mapper;

    public XCustomer fetchCustomer(Long mobileNumber) {
      Customer customerFromRepo = customersRepository.findOne(mobileNumber);
      return mapper.map(customerFromRepo, XCustomer.class);
    }

  /*
* @param  searchPattern: date
* @return : Map of date to Procurement Id
* */
  public List<Long> getMobileNumberList(String searchPattern) {
    return customersRepository.findMobileNumberBySearchPattern(searchPattern);
  }


}
