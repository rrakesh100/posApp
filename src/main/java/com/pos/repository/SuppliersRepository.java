package com.pos.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.pos.model.Supplier;

/**
 * Created by rajithar on 14/1/18.
 */
public interface SuppliersRepository extends CrudRepository<Supplier,Long> {

  List<Supplier> findByName(String name);

  //https://stackoverflow.com/questions/20374437/jpa-query-creation-order-by
  @Transactional(readOnly = true)
  List<Supplier> findAllByOrderById();

  List<Supplier> findAllBySearchPattern(String searchPattern);

}