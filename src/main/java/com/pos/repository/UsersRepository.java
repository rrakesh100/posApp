package com.pos.repository;

import com.pos.model.Customer;
import com.pos.security.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by rrampall on 15/03/18.
 */
public interface UsersRepository extends CrudRepository<User, String> {


    public User findByEmail(String email);

    @Override
    void delete(User user);


}
