package com.pos.controller;

import com.pos.model.*;
import com.pos.repository.SalesRepository;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rrampall on 19/12/17.
 */
@Controller
public class HomeController {

    @Autowired
    private SalesRepository salesRepository;

    @RequestMapping("/hello")
    public @ResponseBody  String sayHello(){
        Sale sale = new Sale();
        SaleItem s1 = new SaleItem();
        Item i = new Item();
        i.setUid("100200300407");
        s1.setItem(i);
        s1.setSerialNumber(1L);
        s1.setQuantity((double) 20);
        s1.setSale(sale);
//
//        SaleItem s2 = new SaleItem();
//        s2.setItemId(12);
//        s2.setSerialNumber(2L);

        List<SaleItem> l = new ArrayList<>();
        l.add(s1);
      //  l.add(s2);

        sale.setSaleItems(l);
        sale.setInvoiceNumber("1234");
     //   sale.setSaleId(1L);
        Employee e = new Employee();
        e.setId(1);
        sale.setEmployee(e);
        sale.setSaleTime(new Date());
        sale.setSubTotal((double) 124);
        sale.setTotal((double) 345);
        sale.setDiscount((double) 0);
        sale.setPaymentType("cash");
        Customer c = new Customer();
        c.setMobileNumber("7981008285");
        sale.setCustomer(c);

        salesRepository.save(sale);

        return "Hello world !!! @@@@";
    }

    @RequestMapping("/bye")
    public @ResponseBody  String sayBye(){

        salesRepository.delete("1234");

        return "Hello world !!! @@@@";
    }

//    public static void main(String[] args) {
//
//    }

}
