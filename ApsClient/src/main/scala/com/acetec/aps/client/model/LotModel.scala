package com.acetec.aps.client.model

import com.acetec.aps.share.dto.LotDto

import scalafx.beans.property.{ObjectProperty, StringProperty}

case class LotModel(lot: LotDto) {
  val lotNumber = new StringProperty(this, "lotNumber", lot.lotNumber)
  val uniquePN = new StringProperty(this, "uniquePN", lot.uniquePn)
  val customer = new StringProperty(this, "customer", lot.customer)
  val customerPN = new StringProperty(this, "customerPN", lot.customerPn)
  val process = new StringProperty(this, "process", lot.process)
  val testerModel = new StringProperty(this, "testerModel", lot.testerModel)
  val hr2Usd = new StringProperty(this, "hr2Usd", lot.hr2Usd)
  val lotSize = new ObjectProperty[Int](this, "lotSize", lot.lotSize)
}
