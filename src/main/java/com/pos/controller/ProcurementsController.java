package com.pos.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pos.commons.Response;
import com.pos.pojos.XProcurement;
import com.pos.service.ProcurementService;

/**
 * Created by rrampall on 22/12/17.
 */
@RestController
//Allowing from all for now
@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/v1")
public class ProcurementsController {

  @Autowired
  private ProcurementService procurementService;

  @GetMapping(value="procurements")
  public Map<Date, List<Long>> listAll(){
    return procurementService.getAllProcurements();
  }

  @GetMapping(value="procurements/{procurementId}")
  public @ResponseBody XProcurement fetchItem(@PathVariable(value="procurementId") String procurementId ){
    return procurementService.fetchProcurement(Long.valueOf(procurementId));
  }

  @PutMapping(value="procurements")
  public @ResponseBody
  ResponseEntity<HttpStatus> editItem(@RequestBody XProcurement xProcurement){
    procurementService.editProcurement(xProcurement);
    return new Response<HttpStatus>().noContent().build();
  }

  @PostMapping (value="procurements")
  public void addItem(@RequestBody XProcurement xProcurement){
    procurementService.addProcurement(xProcurement);
  }
}
