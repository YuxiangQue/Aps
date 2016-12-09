package com.acetec.aps.server.dao;

import com.acetec.aps.server.entity.Role;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface RoleDao extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
