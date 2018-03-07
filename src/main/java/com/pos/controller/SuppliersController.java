package com.pos.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.pos.commons.Response;
import com.pos.pojos.XSupplier;
import com.pos.service.SupplierService;

/**
 * Created by rrampall on 22/12/17.
 */
@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/v1")
public class SuppliersController {
  @Autowired
  private SupplierService supplierService;

  @GetMapping(value="suppliers")
  public @ResponseBody List<XSupplier> listAll(){
    return supplierService.getAllSuppliers();
  }

  @GetMapping(value="suppliers/{supplierId}")
  public @ResponseBody
  XSupplier fetchSupplier(@PathVariable(value="supplierId") String supplierId ){
    XSupplier it =  supplierService.fetchSupplier(supplierId);
    return it;
  }

  @PutMapping(value="suppliers")
  public @ResponseBody
  ResponseEntity<HttpStatus> editSupplier(@RequestBody XSupplier supplier){
    supplierService.editSupplier(supplier);
    return new Response<HttpStatus>().noContent().build();
  }

  @PostMapping (value="suppliers")
  public @ResponseBody ResponseEntity<HttpStatus> addSupplier(@RequestBody XSupplier supplier){
    supplierService.addSupplier(supplier);
    return new Response<HttpStatus>().noContent().status(HttpStatus.CREATED.value()).build();
  }

  @GetMapping(value="filteredSuppliers")
  public @ResponseBody Map<String, Long> getFilteredSuppliers(@RequestParam(value="searchPattern")
    String searchPattern) {
    return supplierService.getSupplierNameAndIdMapping(searchPattern);
  }

  @DeleteMapping(value="suppliers/{id}")
  public @ResponseBody
  ResponseEntity<HttpStatus> deleteItem(@PathVariable(value="id") String supplierId){
    supplierService.deleteSupplier(supplierId);
    return new Response<HttpStatus>().noContent().build();
  }
}
