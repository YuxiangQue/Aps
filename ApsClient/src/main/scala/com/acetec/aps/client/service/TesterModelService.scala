package com.acetec.aps.client.service

import com.acetec.aps.share.dto.TesterModelDto
import rx.lang.scala.Observable

trait TesterModelService {

  def findAllTesterModels(): Observable[List[TesterModelDto]]
}


