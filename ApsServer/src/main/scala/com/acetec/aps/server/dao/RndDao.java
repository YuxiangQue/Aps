package com.acetec.aps.server.dao;

import com.acetec.aps.server.entity.Rnd;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface RndDao extends CrudRepository<Rnd, Long> {
}
