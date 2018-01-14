package com.pos.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.pos.model.Supplier;
import com.pos.pojos.XSupplier;
import com.pos.repository.SuppliersRepository;

/**
 * Created by rajithar on 14/1/18.
 */
public class SupplierService {

  @Autowired
  private SuppliersRepository suppliersRepository;

  @Autowired
  private Mapper mapper;

  public List<XSupplier> getAllSuppliers(){
    List<Supplier> supplierList = suppliersRepository.findAllByOrderById();
    List<XSupplier> xSupplierList = new ArrayList<>();
    for(Supplier supplier : supplierList) {
      XSupplier xSupplier = mapper.map(supplier, XSupplier.class);
      xSupplierList.add(xSupplier);
    }
    return xSupplierList;
  }

  public XSupplier fetchSupplier(Long id){
    Supplier supplierFromRepo = suppliersRepository.findOne(id);
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

  //@TODO Might need only name and id here
  public List<XSupplier> getAllSuppliers(String searchPattern) {
    List<Supplier> suppliersList = suppliersRepository.findAllBySearchPattern(searchPattern);
    List<XSupplier> xSupplierList = new ArrayList<>();
    for(Supplier supplier : suppliersList) {
      XSupplier xSupplier = mapper.map(supplier, XSupplier.class);
      xSupplierList.add(xSupplier);
    }
    return xSupplierList;
  }
}
