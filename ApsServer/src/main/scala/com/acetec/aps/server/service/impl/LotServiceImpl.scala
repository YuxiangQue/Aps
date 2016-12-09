package com.acetec.aps.server.service.impl

import com.acetec.aps.server.dao.LotDao
import com.acetec.aps.server.entity.Lot
import com.acetec.aps.server.service.LotService
import com.acetec.aps.share.dto.LotDto
import ma.glasnost.orika.MapperFacade
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.JavaConversions._

@Service
class LotServiceImpl extends LotService {

  @Autowired
  val lotDao: LotDao = null

  @Autowired
  val mapper: MapperFacade = null

  override def saveLot(lot: LotDto): LotDto = {
    val entity = mapper.map(lot, classOf[Lot])
    entity.flow.lot = entity
    lotDao.save(entity)
    mapper.map(entity, classOf[LotDto])
  }

  def findAllLots(): List[LotDto] = {
    val lots = lotDao.findAll()
    lots.map(p => mapper.map(p, classOf[LotDto])).toList
  }
}
