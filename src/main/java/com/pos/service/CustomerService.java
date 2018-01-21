package com.pos.service;

import java.util.List;

import com.pos.model.Customer;
import com.pos.pojos.XCustomer;
import com.pos.repository.CustomersRepository;

import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
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

  public void editCustomer(XCustomer xCustomer){
    Customer customerFromRequest = mapper.map(xCustomer, Customer.class);
    Customer customerFromRepo = customersRepository.findOne(customerFromRequest.getMobileNumber());
    BeanUtils.copyProperties(customerFromRepo,customerFromRequest, ServiceUtils.getNullPropertyNames(customerFromRequest) );
    customersRepository.save(customerFromRequest);
  }

  public void addCustomer(XCustomer xCustomer){
    Customer customer = mapper.map(xCustomer, Customer.class);
    System.out.println(customer);
    customersRepository.save(customer);
  }
    /*
  * @param  searchPattern: Mobile No
  * @return : List of Mobile No's matching the pattern
  * */
  public List<Long> getMobileNumberList(String searchPattern) {
    return customersRepository.findMobileNumberBySearchPattern(searchPattern);
  }
}
