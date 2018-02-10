package com.pos.repository;

import com.pos.model.DailySale;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rrampall on 10/02/18.
 */
public interface DailySaleHistoryRepository  extends CrudRepository<DailySale, Long> {
}
