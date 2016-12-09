package com.acetec.aps.client.service

import com.acetec.aps.share.dto.LotDto
import rx.lang.scala.Observable

trait LotService {
  def findAllLots(): Observable[List[LotDto]]
}

