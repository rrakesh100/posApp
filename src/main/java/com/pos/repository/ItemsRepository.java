package com.pos.repository;

import com.pos.model.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by rrampall on 19/12/17.
 */
public interface ItemsRepository extends CrudRepository<Item,Long> {

    List<Item> findByName(String name);


    // custom query example and return a stream
//    @Query("select c from Customer c where c.email = :email")
//    Stream<Customer> findByEmailReturnStream(@Param("email") String email);
}