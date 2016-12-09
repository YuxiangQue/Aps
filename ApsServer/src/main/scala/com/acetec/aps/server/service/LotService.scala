package com.acetec.aps.server.service

import com.acetec.aps.share.dto.LotDto

trait LotService {
  def saveLot(lot: LotDto): LotDto

  def findAllLots(): List[LotDto]
}

