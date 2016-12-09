package com.acetec.aps.client.model


import com.acetec.aps.share.dto.ProductDto

import scalafx.beans.property.{ObjectProperty, StringProperty}

case class ProductModel(product: ProductDto) {
  val uniquePn = StringProperty(product.uniquePn)
  val customer = StringProperty(product.customer)
  val process = StringProperty(product.process)
  val customerPn = StringProperty(product.customerPn)
  val testerModel = StringProperty(product.testerModel)
  val formFactor = StringProperty(product.formFactor)
  val lotSize = ObjectProperty(product.lotSize)
  val dpw = ObjectProperty(product.dpw)
  val yield1 = ObjectProperty(product.yield1)
  val parallelSite1 = ObjectProperty(product.parallelSite1)
  val touchDownEff1 = ObjectProperty(product.touchDownEff1)
  val hr1Usd = ObjectProperty(product.hr1Usd)
  val indexTime1Sec = ObjectProperty(product.indexTime1Sec)
  val testTime1 = ObjectProperty(product.testTime1)
  val parallelSite2 = ObjectProperty(product.parallelSite2)
  val touchDownEff2 = ObjectProperty(product.touchDownEff2)
  val hr2Usd = ObjectProperty(product.hr2Usd)
  val indexTime2Sec = ObjectProperty(product.indexTime2Sec)
  val testTime2 = ObjectProperty(product.testTime2)
}







