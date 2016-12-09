package com.acetec.aps.share.dto

import java.util

import scala.beans.BeanProperty

class TesterPlatformDto {
  @BeanProperty
  var name: String = _
  @BeanProperty
  var testers: util.List[TesterDto] = _
}
