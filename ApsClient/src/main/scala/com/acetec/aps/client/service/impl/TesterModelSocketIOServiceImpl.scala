package com.acetec.aps.client.service.impl

import com.acetec.aps.client.service.{SocketIOService, TesterModelService}
import com.acetec.aps.share.Protocol._
import com.acetec.aps.share.dto.TesterModelDto
import com.google.inject.Inject
import rx.lang.scala.Observable

class TesterModelSocketIOServiceImpl @Inject()(socketIOService: SocketIOService) extends TesterModelService {
  override def findAllTesterModels(): Observable[List[TesterModelDto]] = {
    socketIOService.requestTemplate[FindAllTesterModelsRequest, FindAllTesterModelsResponse](null, FindAllTesterModels).map(resp => {
      resp.testerModels
    })
  }
}
