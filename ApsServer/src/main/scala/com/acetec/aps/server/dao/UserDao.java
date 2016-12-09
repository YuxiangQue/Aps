package com.acetec.aps.server.dao;

import com.acetec.aps.server.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserDao extends CrudRepository<User, java.lang.Long> {

    User findByUsername(String username);
}
