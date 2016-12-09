package com.acetec.aps.server.service

import com.acetec.aps.share.dto.TesterModelDto

trait TesterModelService {

  def saveModel(testerModel: TesterModelDto): TesterModelDto

  def findTesterModelByTesterModelName(testerModelName: String): TesterModelDto

  def findAllTesterModels(): List[TesterModelDto]
}
