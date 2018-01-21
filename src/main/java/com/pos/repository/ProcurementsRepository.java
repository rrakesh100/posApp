package com.pos.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.pos.model.Procurement;

/**
 * Created by rajithar on 13/1/18.
 */
public interface ProcurementsRepository extends CrudRepository<Procurement, Long>{

  @Query("select time, id from Procurement p")
  Map<Date, Long> findAllProcurementIds();

  @Query("select time, id from Procurement p where p.time = ':date'")
  Map<Date, Long> findDateIdBySearchPattern(@Param("date") String date);

}
