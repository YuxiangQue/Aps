package com.acetec.aps.server.dao

import javax.transaction.Transactional

import com.acetec.aps.server.entity
import org.springframework.data.repository.CrudRepository

@Transactional
trait ProductDao extends CrudRepository[entity.Product_, java.lang.Long] {
  def findByUniquePn(uniquePn: String): entity.Product_
}
