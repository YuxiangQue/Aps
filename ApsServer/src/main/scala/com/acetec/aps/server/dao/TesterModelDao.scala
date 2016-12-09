package com.acetec.aps.server.dao

import javax.transaction.Transactional

import com.acetec.aps.server.entity.TesterModel
import org.springframework.data.repository.CrudRepository

@Transactional trait TesterModelDao extends CrudRepository[TesterModel, java.lang.Long] {
  def findByName(modelName: String): TesterModel
}
