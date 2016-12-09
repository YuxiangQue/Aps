package com.acetec.aps.server.service.impl

import com.acetec.aps.server.dao.{TesterDao, TesterModelDao, TesterPlatformDao}
import com.acetec.aps.server.entity.TesterPlatform
import com.acetec.aps.server.service.TesterPlatformService
import com.acetec.aps.share.dto.TesterPlatformDto
import ma.glasnost.orika.MapperFacade
import org.springframework.beans.factory.annotation.Autowired

import scala.collection.JavaConversions._

class TesterPlatformServiceImpl extends TesterPlatformService {

  @Autowired
  var modelDao: TesterModelDao = _

  @Autowired
  var platformDao: TesterPlatformDao = _

  @Autowired
  var machineDao: TesterDao = _

  @Autowired
  var modelMapper: MapperFacade = _

  override def createTesterPlatform(testerModelName: String, testerPlatform: TesterPlatformDto): TesterPlatformDto = {
    val entity = modelMapper.map(testerPlatform, classOf[TesterPlatform])
    val model = modelDao.findByName(testerModelName)
    entity.testerModel = model
    platformDao.save(entity)
    modelMapper.map(entity, classOf[TesterPlatformDto])
  }

  override def findTesterPlatformByTesterPlatformName(testerPlatformName: String): TesterPlatformDto = {
    val platform = platformDao.findByName(testerPlatformName)
    modelMapper.map(platform, classOf[TesterPlatformDto])
  }

  override def findTesterPlatformsByTesterModelName(testerModelName: String): List[TesterPlatformDto] = {
    val platforms = platformDao.findByTesterModelName(testerModelName)
    platforms.map(p => modelMapper.map(p, classOf[TesterPlatformDto])).toList
  }
}
