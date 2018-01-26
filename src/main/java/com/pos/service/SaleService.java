package com.pos.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pos.model.SaleItem;
import com.pos.pojos.XSaleItem;
import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pos.model.Sale;
import com.pos.pojos.XSale;
import com.pos.repository.SalesRepository;

import static com.pos.commons.EntityType.Sales;

/**
 * Created by rajithar on 21/1/18.
 */
@Service
public class SaleService {

  @Autowired
  private SalesRepository salesRepository;

  @Autowired
  private Mapper mapper;

  public List<XSale>  getAllSaleIds() {
    List<Sale>  allSales = salesRepository.findAll();
    List<XSale> returnList = new ArrayList<>();
    for(Sale s : allSales) {
      returnList.add(mapper.map(s, XSale.class));
    }
    return returnList;
  }

  public XSale fetchSale(String saleId) {
    Sale sale = salesRepository.findOne(saleId);
    XSale xs = mapper.map(sale, XSale.class); List<XSaleItem> k = new ArrayList<>();
    for(SaleItem si : sale.getSaleItems()) {
      XSaleItem xsItem = mapper.map(si, XSaleItem.class);
      xs.getSaleItems().add(xsItem);
    }
    return xs;
  }

  public void editSale(XSale xSale){
    Sale saleFromRequest = mapper.map(xSale, Sale.class);
    Sale saleFromRepo = salesRepository.findOne(saleFromRequest.getInvoiceNumber());
    BeanUtils.copyProperties(saleFromRepo,saleFromRequest, ServiceUtils.getNullPropertyNames(saleFromRequest) );
    salesRepository.save(saleFromRequest);
  }

  public void addSale(XSale xSale){

    String invoiceNumber = generateInvoiceNumber(xSale);

    Sale sale = mapper.map(xSale, Sale.class);
    sale.setInvoiceNumber(invoiceNumber);
    sale.setSaleTime(new Date());
    for(int i=0;i<sale.getSaleItems().size();i++) {
      SaleItem saleItem = sale.getSaleItems().get(i);
      saleItem.setSerialNumber(i+1);
      saleItem.setSale(sale);
    }
    salesRepository.save(sale);
  }

  private String generateInvoiceNumber(XSale xSale) {
    LocalDateTime localDateTime = LocalDateTime.now();
     String invoiceNNumber = localDateTime.getDayOfMonth() + localDateTime.getMonth().toString().substring(0,3).toUpperCase() +
            localDateTime.getYear() % 2000 + "-" +
            xSale.getCustomerName().substring(0,3).toUpperCase() +
            xSale.getCustomerMobileNumber().substring(xSale.getCustomerMobileNumber().length()-3,xSale.getCustomerMobileNumber().length()) +
            "-" + localDateTime.getHour() + localDateTime.getMinute()+ localDateTime.getSecond();

    return invoiceNNumber;
  }

  /*
* @param date : date
* @return : Map of date to Sale Id
* */
  public Map<Date, String> getSaleDateAndIdMapping(String date) {
    return salesRepository.findDateIdBySearchPattern(date);
  }
}
