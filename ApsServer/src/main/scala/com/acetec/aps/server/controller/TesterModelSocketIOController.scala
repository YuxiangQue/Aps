package com.acetec.aps.server.controller

import javax.annotation.PostConstruct

import com.acetec.aps.server.service.TesterModelService
import com.acetec.aps.share.Protocol._
import com.acetec.aps.share.ResponseWrapper
import com.corundumstudio.socketio.{AckRequest, SocketIOClient, SocketIOServer}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TesterModelSocketIOController {

  private val logger = LoggerFactory.getLogger(classOf[ProductSocketIOController])

  @Autowired
  var testerModelService: TesterModelService = _

  @Autowired
  private var server: SocketIOServer = _

  @PostConstruct
  def addEventListener(): Unit = {
    server.addEventListener(s"request/${FindAllTesterModels}", classOf[FindAllTesterModelsRequest], (client: SocketIOClient, data: FindAllTesterModelsRequest, ackSender: AckRequest) => {
      logger.info(s"request/${FindAllTesterModels}")
      var testerModels = testerModelService.findAllTesterModels()
      client.sendEvent(s"response/${FindAllTesterModels}", ResponseWrapper(0, FindAllTesterModelsResponse(testerModels)))
    })
  }
}
