package com.pos.repository;

import com.pos.model.Customer;
import com.pos.model.ItemUpdate;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rrampall on 10/02/18.
 */
public interface ItemUpdatesRepository extends CrudRepository<ItemUpdate, Long> {

}
