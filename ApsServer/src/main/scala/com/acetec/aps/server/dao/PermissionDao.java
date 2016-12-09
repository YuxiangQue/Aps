package com.acetec.aps.server.dao;

import com.acetec.aps.server.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface PermissionDao extends JpaRepository<Permission, Long> {

    Permission findByName(String name);
}
