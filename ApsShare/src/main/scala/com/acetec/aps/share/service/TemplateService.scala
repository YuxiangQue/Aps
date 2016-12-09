package com.acetec.aps.share.service

import java.io.InputStream

import com.acetec.aps.share.dto.{LotDto, ProductDto, TesterModelDto}

trait TemplateService {

  def importProduct(is: InputStream): List[ProductDto]

  def loadLot(is: InputStream): List[LotDto]

  def loadModel(is: InputStream): List[TesterModelDto]
}
