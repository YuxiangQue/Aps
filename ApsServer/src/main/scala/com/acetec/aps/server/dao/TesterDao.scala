package com.acetec.aps.server.dao

import java.util
import javax.transaction.Transactional

import com.acetec.aps.server.entity.Tester
import org.springframework.data.repository.CrudRepository

@Transactional trait TesterDao extends CrudRepository[Tester, java.lang.Long] {

  def findByTesterPlatformName(testerPlatformName: String): util.List[Tester]

  def findByName(testerName: String): Tester
}
