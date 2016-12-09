package com.acetec.aps.server.service.impl

import com.acetec.aps.server.dao.{TesterDao, TesterModelDao, TesterPlatformDao}
import com.acetec.aps.server.entity.TesterModel
import com.acetec.aps.server.service.TesterModelService
import com.acetec.aps.share.dto.TesterModelDto
import ma.glasnost.orika.MapperFacade
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.JavaConversions._


@Service
class TesterModelServiceImpl extends TesterModelService {

  @Autowired
  var modelDao: TesterModelDao = _

  @Autowired
  var platformDao: TesterPlatformDao = _

  @Autowired
  var machineDao: TesterDao = _

  @Autowired
  var modelMapper: MapperFacade = _

  override def saveModel(testerModel: TesterModelDto): TesterModelDto = {
    val entity = modelMapper.map(testerModel, classOf[TesterModel])
    entity.testerPlatforms.forEach(p => {
      p.testerModel = entity
      p.testers.forEach(t => {
        t.testerPlatform = p
      })
    })
    modelDao.save(entity)
    modelMapper.map(entity, classOf[TesterModelDto])
  }

  override def findTesterModelByTesterModelName(testerModelName: String): TesterModelDto = {
    val model = modelDao.findByName(testerModelName)
    if (model == null) null else modelMapper.map(model, classOf[TesterModelDto])
  }

  override def findAllTesterModels(): List[TesterModelDto] = {
    val models = modelDao.findAll()
    models.map(p => modelMapper.map(p, classOf[TesterModelDto])).toList
  }
}
