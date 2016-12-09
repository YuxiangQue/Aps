package com.acetec.aps.server.dao;

import com.acetec.aps.server.entity.RndLimitation;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface RndLimitationDao extends CrudRepository<RndLimitation, Long> {
}
