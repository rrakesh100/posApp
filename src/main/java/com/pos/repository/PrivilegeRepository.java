package com.pos.repository;

import com.pos.security.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rrampall on 15/03/18.
 */
public interface PrivilegeRepository  extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

    @Override
    void delete(Privilege privilege);

}