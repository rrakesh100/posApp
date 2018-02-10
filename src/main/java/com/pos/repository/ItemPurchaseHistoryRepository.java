package com.pos.repository;

import com.pos.model.ItemPurchaseHistory;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rrampall on 10/02/18.
 */
public interface ItemPurchaseHistoryRepository extends CrudRepository<ItemPurchaseHistory, Long> {
}
