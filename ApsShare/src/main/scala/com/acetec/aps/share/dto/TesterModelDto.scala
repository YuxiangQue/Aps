package com.acetec.aps.share.dto

import java.util

import scala.beans.BeanProperty

class TesterModelDto() {
  @BeanProperty
  var name: String = _
  @BeanProperty
  var testerPlatforms: util.List[TesterPlatformDto] = _
}
