package com.pos.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.pos.model.Supplier;

/**
 * Created by rajithar on 14/1/18.
 */
public interface SuppliersRepository extends CrudRepository<Supplier,Long> {

  List<Supplier> findByAgencyName(String agencyName);

  //https://stackoverflow.com/questions/20374437/jpa-query-creation-order-by
  @Transactional(readOnly = true)
  List<Supplier> findAllByOrderById();

  @Query("select agencyName, id from Supplier s where s.agencyName like ':searchPattern'")
  Map<String, Long> findNameIdBySearchPattern(@Param("searchPattern") String searchPattern);

}