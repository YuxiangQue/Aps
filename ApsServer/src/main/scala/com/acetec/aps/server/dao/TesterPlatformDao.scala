package com.acetec.aps.server.dao

import javax.transaction.Transactional

import com.acetec.aps.server.entity.TesterPlatform
import org.springframework.data.repository.CrudRepository

@Transactional
trait TesterPlatformDao extends CrudRepository[TesterPlatform, java.lang.Long] {
  def findByTesterModelName(testerModelName: String): java.util.List[TesterPlatform]

  def findByName(testerPlatformName: String): TesterPlatform
}
