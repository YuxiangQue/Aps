package com.acetec.aps.server.service.impl

import com.acetec.aps.server.dao.{TesterDao, TesterModelDao, TesterPlatformDao}
import com.acetec.aps.server.entity.Tester
import com.acetec.aps.server.service.TesterService
import com.acetec.aps.share.dto.TesterDto
import ma.glasnost.orika.MapperFacade
import org.springframework.beans.factory.annotation.Autowired

import scala.collection.JavaConversions._

class TesterServiceImpl extends TesterService {

  @Autowired
  var modelDao: TesterModelDao = _

  @Autowired
  var platformDao: TesterPlatformDao = _

  @Autowired
  var machineDao: TesterDao = _

  @Autowired
  var modelMapper: MapperFacade = _

  override def createTester(testerPlatformName: String, tester: TesterDto): TesterDto = {
    val entity = modelMapper.map(tester, classOf[Tester])
    val testerPlatform = platformDao.findByName(testerPlatformName)
    entity.testerPlatform = testerPlatform
    machineDao.save(entity)
    modelMapper.map(entity, classOf[TesterDto])
  }

  override def findTesterByTesterName(testerName: String): TesterDto = {
    val tester = machineDao.findByName(testerName)
    val dto = modelMapper.map(tester, classOf[TesterDto])
    dto
  }

  override def findTestersByTesterPlatformName(testerPlatformName: String): List[TesterDto] = {
    val testers = machineDao.findByTesterPlatformName(testerPlatformName)
    testers.map(t => modelMapper.map(t, classOf[TesterDto])).toList
  }
}
