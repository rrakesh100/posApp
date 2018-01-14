package com.pos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pos.commons.Response;
import com.pos.pojos.XSupplier;
import com.pos.service.SupplierService;

/**
 * Created by rrampall on 22/12/17.
 */
public class SuppliersController {
  @Autowired
  private SupplierService supplierService;

  @GetMapping(value="suppliers")
  public List<XSupplier> listAll(){
    return supplierService.getAllSuppliers();
  }

  @GetMapping(value="suppliers/{supplierId}")
  public @ResponseBody
  XSupplier fetchSupplier(@PathVariable(value="supplierId") String supplierId ){
    XSupplier it =  supplierService.fetchSupplier(Long.valueOf(supplierId));
    return it;
  }

  @PutMapping(value="suppliers")
  public @ResponseBody
  ResponseEntity<HttpStatus> editSupplier(@RequestBody XSupplier supplier){
    supplierService.editSupplier(supplier);
    return new Response<HttpStatus>().noContent().build();
  }

  @PostMapping (value="suppliers")
  public void addSupplier(@RequestBody XSupplier supplier){
    supplierService.addSupplier(supplier);
  }

  @GetMapping(value="suppliers")
  public @ResponseBody List<XSupplier> getFilteredSuppliers(@RequestParam(required = false, defaultValue = "*") String searchPattern) {
    return supplierService.getAllSuppliers(searchPattern);
  }
}
