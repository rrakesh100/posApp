package com.pos.controller;

import com.pos.commons.Response;
import com.pos.pojos.XCustomer;
import com.pos.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by rrampall on 22/12/17.
 */
@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/v1")
public class CustomersController {


    @Autowired
    private CustomerService customerService;


    @GetMapping(value="customers/{mobileNumber}")
    public @ResponseBody
    XCustomer fetchCustomer(@PathVariable(value="mobileNumber") String mobileNumber ){
        return customerService.fetchCustomer(Long.valueOf(mobileNumber));
    }

    @PutMapping(value="customers")
    public @ResponseBody
    ResponseEntity<HttpStatus> editCustomer(@RequestBody XCustomer xCustomer){
        customerService.editCustomer(xCustomer);
        return new Response<HttpStatus>().noContent().build();
    }

    @PostMapping (value="customers")
    public void addCustomer(@RequestBody XCustomer xCustomer){
        customerService.addCustomer(xCustomer);
    }

    @GetMapping(value="filteredCustomers")
    public @ResponseBody
    List<Long> getFilteredMobileNumbers(@RequestParam(value="searchPattern") String searchPattern) {
        return customerService.getMobileNumberList(searchPattern);
    }
}
