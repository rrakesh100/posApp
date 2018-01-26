package com.pos.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.pos.model.Sale;

/**
 * Created by rajithar on 21/1/18.
 */
public interface SalesRepository extends CrudRepository<Sale, String> {


  //write a query to get records from last 30 days
  List<Sale> findAll();

  @Query("select saleTime, invoiceNumber from Sale s where s.saleTime = ':date'")
  Map<Date, String> findDateIdBySearchPattern(@Param("date") String date);

}
