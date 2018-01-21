package com.pos.service;

import java.util.Date;
import java.util.Map;

import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pos.model.Sale;
import com.pos.pojos.XSale;
import com.pos.repository.SalesRepository;

/**
 * Created by rajithar on 21/1/18.
 */
@Service
public class SaleService {

  @Autowired
  private SalesRepository salesRepository;

  @Autowired
  private Mapper mapper;

  public Map<Date, Long> getAllSaleIds() {
    return salesRepository.findAllSaleIds();
  }

  public XSale fetchSale(Long saleId) {
    Sale sale = salesRepository.findOne(saleId);
    return mapper.map(sale, XSale.class);
  }

  public void editSale(XSale xSale){
    Sale saleFromRequest = mapper.map(xSale, Sale.class);
    Sale saleFromRepo = salesRepository.findOne(saleFromRequest.getSaleId());
    BeanUtils.copyProperties(saleFromRepo,saleFromRequest, ServiceUtils.getNullPropertyNames(saleFromRequest) );
    salesRepository.save(saleFromRequest);
  }

  public void addSale(XSale xSale){
    Sale sale = mapper.map(xSale, Sale.class);
    System.out.println(sale);
    salesRepository.save(sale);
  }

  /*
* @param date : date
* @return : Map of date to Sale Id
* */
  public Map<Date, Long> getSaleDateAndIdMapping(String date) {
    return salesRepository.findDateIdBySearchPattern(date);
  }
}
