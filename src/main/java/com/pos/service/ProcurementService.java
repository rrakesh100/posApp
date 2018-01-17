package com.pos.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pos.model.Procurement;
import com.pos.pojos.XProcurement;
import com.pos.repository.ProcurementsRepository;

/**
 * Created by rajithar on 13/1/18.
 */
@Service
public class ProcurementService {

  @Autowired
  private ProcurementsRepository procurementsRepository;

  @Autowired
  private Mapper mapper;

  public Map<Date, Long> getAllProcurementIds() {
    return procurementsRepository.findAllProcurementIds();
  }

  public XProcurement fetchProcurement(Long procurementId) {
    Procurement procurement = procurementsRepository.findOne(procurementId);
    return mapper.map(procurement, XProcurement.class);
  }

  public void editProcurement(XProcurement xProcurement){
    Procurement procurementFromRequest = mapper.map(xProcurement, Procurement.class);
    Procurement procurementFromRepo = procurementsRepository.findOne(procurementFromRequest.getProcurementId());
    BeanUtils.copyProperties(procurementFromRepo,procurementFromRequest, ServiceUtils.getNullPropertyNames(procurementFromRequest) );
    procurementsRepository.save(procurementFromRequest);
  }

  public void addProcurement(XProcurement xProcurement){
    Procurement procurement = mapper.map(xProcurement, Procurement.class);
    System.out.println(procurement);
    procurementsRepository.save(procurement);
  }

  /*
* @param date : date
* @return : Map of date to Procurement Id
* */
  public Map<Date, Long> getProcurementDateAndIdMapping(String date) {
    return procurementsRepository.findNameBySearchPattern(date);
  }
}
