package com.pos.service;

import java.util.ArrayList;
import java.util.List;

import com.pos.model.Customer;
import com.pos.model.Item;
import com.pos.pojos.XCustomer;
import com.pos.pojos.XItem;
import com.pos.repository.CustomersRepository;

import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rrampall on 18/12/17.
 */
@Service
public class CustomerService {

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private Mapper mapper;

    public XCustomer fetchCustomer(String mobileNumber) {
      Customer customerFromRepo = customersRepository.findOne(mobileNumber);
      return mapper.map(customerFromRepo, XCustomer.class);
    }

    public List<XCustomer> allCustomers() {
        List<Customer> customersFromRepoList = (List<Customer>) customersRepository.findAll();
        List<XCustomer> xCustomersList = new ArrayList<>();

        for(Customer customer : customersFromRepoList) {
            XCustomer xCustomer = mapper.map(customer, XCustomer.class);
            xCustomersList.add(xCustomer);
        }
        return xCustomersList;
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
  public List<String> getMobileNumberList(String searchPattern) {
    List<String> mobs =  customersRepository.findMobileNumberBySearchPattern(searchPattern + "%");
      System.out.println(mobs);
      return mobs;
  }
}
