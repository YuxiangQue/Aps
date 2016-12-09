package com.acetec.aps.server.controller

import javax.annotation.PostConstruct

import com.acetec.aps.server.service.LotService
import com.acetec.aps.share.{FindAllLots, ResponseWrapper}
import com.corundumstudio.socketio.{AckRequest, SocketIOClient, SocketIOServer}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class LotSocketIOController {
  private val logger = LoggerFactory.getLogger(classOf[ProductSocketIOController])

  @Autowired
  var lotService: LotService = _

  @Autowired
  private var server: SocketIOServer = _

  @PostConstruct
  def addEventListener(): Unit = {
    server.addEventListener(s"request/${FindAllLots.name}", classOf[FindAllLots.Request], (client: SocketIOClient, data: FindAllLots.Request, ackSender: AckRequest) => {
      logger.info(s"request/${FindAllLots.name}")
      val lots = lotService.findAllLots()
      client.sendEvent(s"response/${FindAllLots.name}", ResponseWrapper(0, FindAllLots.Response(lots)))
    })
  }
}
