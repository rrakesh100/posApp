package com.pos.controller;

import com.pos.commons.Response;
import com.pos.model.Customer;
import com.pos.pojos.XItem;
import com.pos.service.CustomerService;
import com.pos.service.ItemsService;
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
    Customer fetchItem(@PathVariable(value="mobileNumber") String mobileNumber ){
        return customerService.fetchCustomer(Long.valueOf(mobileNumber));
    }

//    @PutMapping(value="items")
//    public @ResponseBody
//    ResponseEntity<HttpStatus> editItem(@RequestBody XItem item){
//        itemsService.editItem(item);
//        return new Response<HttpStatus>().noContent().build();
//    }
//
//    @PostMapping (value="items")
//    public void addItem(@RequestBody XItem item){
//        itemsService.addItem(item);
//    }
//
//    @GetMapping(value="filteredItems")
//    public @ResponseBody
//    Map<String, Long> getFilteredItems(@RequestParam(value="searchPattern") String searchPattern) {
//        return itemsService.getSupplierNameAndIdMapping(searchPattern);
//    }
}
