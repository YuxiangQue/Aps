package com.acetec.aps.server.dao;

import com.acetec.aps.server.entity.Flow;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface FlowDao extends CrudRepository<Flow, java.lang.Long> {

    Flow findById(long id);
}
