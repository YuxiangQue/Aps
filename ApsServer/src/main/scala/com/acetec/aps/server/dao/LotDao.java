package com.acetec.aps.server.dao;

import com.acetec.aps.server.entity.Lot;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface LotDao extends CrudRepository<Lot, Long> {

    Lot findById(long id);

    Lot findByLotNumber(String lotNumber);
}
