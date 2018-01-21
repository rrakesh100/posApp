package com.pos.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pos.commons.Response;
import com.pos.pojos.XSale;
import com.pos.service.SaleService;

/**
 * Created by rrampall on 22/12/17.
 */
@RestController
//Allowing from all for now
@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/v1")
public class SalesController {

  @Autowired
  private SaleService saleService;

  @GetMapping(value="sales")
  public Map<Date, Long> getAllSales(){
    return saleService.getAllSaleIds();
  }

  @GetMapping(value="sales/{saleId}")
  public @ResponseBody
  XSale fetchSale(@PathVariable(value="saleId") String saleId ){
    return saleService.fetchSale(Long.valueOf(saleId));
  }

  @PutMapping(value="sales")
  public @ResponseBody
  ResponseEntity<HttpStatus> editSale(@RequestBody XSale xSale){
    saleService.editSale(xSale);
    return new Response<HttpStatus>().noContent().build();
  }

  @PostMapping (value="sales")
  public void addSale(@RequestBody XSale xSale){
    saleService.addSale(xSale);
  }

  @GetMapping(value="filteredSales")
  public @ResponseBody Map<Date, Long> getFilteredSales(@RequestParam(value="searchPattern")
    String date) {
    return saleService.getSaleDateAndIdMapping(date);
  }
}
