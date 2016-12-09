package com.acetec.aps.client.service.impl

import com.acetec.aps.client.service.{LotService, SocketIOService}
import com.acetec.aps.share.FindAllLots
import com.acetec.aps.share.dto.LotDto
import com.acetec.aps.share.service.TemplateService
import com.google.inject.Inject
import rx.lang.scala.Observable

class LotSocketIOServiceImpl @Inject()(templateService: TemplateService,
                                       socketIOService: SocketIOService) extends LotService {
  override def findAllLots(): Observable[List[LotDto]] = {
    socketIOService.requestTemplate[FindAllLots.Request, FindAllLots.Response](null, FindAllLots.name).map(resp => {
      resp.lots
    })
  }
}
