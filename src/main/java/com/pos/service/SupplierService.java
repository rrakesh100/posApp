package com.pos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pos.model.Item;
import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.pos.model.Supplier;
import com.pos.pojos.XSupplier;
import com.pos.repository.SuppliersRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by rajithar on 14/1/18.
 */
@Service
public class SupplierService {

  @Autowired
  private SuppliersRepository suppliersRepository;

  @Autowired
  private Mapper mapper;

  public List<XSupplier> getAllSuppliers(){
    List<Supplier> supplierList = suppliersRepository.findByDeletedFalseOrderById();
    List<XSupplier> xSupplierList = new ArrayList<>();
    for(Supplier supplier : supplierList) {
      XSupplier xSupplier = mapper.map(supplier, XSupplier.class);
      xSupplierList.add(xSupplier);
    }
    return xSupplierList;
  }

  public XSupplier fetchSupplier(String  id){
    Supplier supplierFromRepo = suppliersRepository.findOne(Long.valueOf(id));
    return mapper.map(supplierFromRepo, XSupplier.class);
  }

  public void editSupplier(XSupplier xSupplier){
    Supplier supplierFromRequest = mapper.map(xSupplier, Supplier.class);
    Supplier supplierFromRepo = suppliersRepository.findOne(xSupplier.getId());
    BeanUtils.copyProperties(supplierFromRepo,supplierFromRequest, ServiceUtils.getNullPropertyNames(supplierFromRequest) );
    suppliersRepository.save(supplierFromRequest);
  }

  public void addSupplier(XSupplier xSupplier){
    Supplier supplier = mapper.map(xSupplier, Supplier.class);
    System.out.println(supplier);
    suppliersRepository.save(supplier);
  }

  /*
  * @param searchPattern : supplier name pattern
  * @return : Map of Supplier name to Supplier Id
  * */
  public Map<String, Long> getSupplierNameAndIdMapping(String searchPattern) {
    return suppliersRepository.findNameIdBySearchPattern(searchPattern);
  }


  public void deleteSupplier(String supplierId) {
    //we only do a soft delete of the item. so just mark the deleted flag = true
    Supplier supplierFromRepo = suppliersRepository.findOne(Long.valueOf(supplierId));
    supplierFromRepo.setDeleted(true);
    suppliersRepository.save(supplierFromRepo);
  }

}
