package com.pos.repository;

import com.pos.model.Item;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by rrampall on 19/12/17.
 */
public interface ItemsRepository extends CrudRepository<Item,String> {

    List<Item> findByNameAndDeletedFalse(String name);

    //https://stackoverflow.com/questions/20374437/jpa-query-creation-order-by
    @Transactional(readOnly=true)
    List<Item> findByDeletedFalseOrderByName();


    /*
       spring jpa is expecting as for each column why ??

      o/p for searchpattern=god will be something like

        {"uid":"100200300401","name":"godavaririceandi"}

        But we want { "100200300401": "godavaririceandi" }

      @Query("select i.name as name, i.uid as uid from Item i where i.name like (:searchPattern)")
      Map<String,String> findNameIdBySearchPattern(@Param("searchPattern") String searchPattern);

      */

    @Query("select i from Item i where lower(i.name) like lower(:searchPattern) and i.deleted=false")
    List<Item> findAllItemsWithName(@Param("searchPattern") String searchPattern);



    @Query("select i from Item i where lower(i.sku) like lower(:searchPattern) and i.deleted=false")
    List<Item> findAllItemsWithSKU(@Param("searchPattern") String searchPattern);




    // custom query example and return a stream
//    @Query("select c from Customer c where c.email = :email")
//    Stream<Customer> findByEmailReturnStream(@Param("email") String email);
}