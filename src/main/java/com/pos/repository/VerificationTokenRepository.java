package com.pos.repository;

import com.pos.model.VerificationToken;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rrampall on 15/03/18.
 */
public interface VerificationTokenRepository extends CrudRepository<VerificationToken,String{
}
