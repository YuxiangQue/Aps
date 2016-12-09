package com.acetec.aps.share.dto

import scala.beans.BeanProperty

class LotDto() {
  @BeanProperty
  var buCode1: String = _
  @BeanProperty
  var buCode2: String = _
  @BeanProperty
  var lotNumber: String = _
  @BeanProperty
  var testerSn1: String = _
  @BeanProperty
  var testerSn2: String = _
  @BeanProperty
  var testerModel: String = _
  @BeanProperty
  var uniquePn: String = _
  @BeanProperty
  var customer: String = _
  @BeanProperty
  var customerPn: String = _
  @BeanProperty
  var process: String = _
  @BeanProperty
  var hr2Usd: String = _
  @BeanProperty
  var lotSize: Int = _
  @BeanProperty
  var formFactor: String = _
  @BeanProperty
  var passQty: Int = _
  @BeanProperty
  var yield1: Double = _
  @BeanProperty
  var flow: FlowDto = _
}
