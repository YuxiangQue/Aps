package com.acetec.aps.share.dto

import java.util.Date

import scala.beans.BeanProperty

class FlowDto {
  @BeanProperty
  var incomingDate: Date = _
  @BeanProperty
  var moveInDate: Date = _
  @BeanProperty
  var lotCreateDate: Date = _
  @BeanProperty
  var testStartDate: Date = _
  @BeanProperty
  var testEndDate: Date = _
  @BeanProperty
  var pt1StartDate: Date = _
  @BeanProperty
  var pt1EndDate: Date = _
  @BeanProperty
  var pt2StartDate: Date = _
  @BeanProperty
  var pt2EndDate: Date = _
  @BeanProperty
  var moveOutDate: Date = _
  @BeanProperty
  var outgoingDate: Date = _
}
