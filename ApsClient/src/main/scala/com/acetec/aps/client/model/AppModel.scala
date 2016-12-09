package com.acetec.aps.client.model

import scalafx.beans.property.DoubleProperty
import scalafx.collections.ObservableBuffer

class AppModel() {

  val listViewItems = new ObservableBuffer[String]()
  val choiceBoxItems = ObservableBuffer("Choice A", "Choice B", "Choice C", "Choice D")
  val maxRpm: Double = 8000d
  val rpm = new DoubleProperty(this, "rpm", 0)
  val maxKph: Double = 300.0
  val kph = new DoubleProperty(this, "kph", 0)

  def randomWebSite(): String = {

    val webSites: Array[String] = Array(
      "http://javafx.com",
      "http://fxexperience.com",
      "http://steveonjava.com",
      "http://javafxpert.com",
      "http://pleasingsoftware.blogspot.com",
      "http://www.weiqigao.com/blog",
      "http://google.com"
    )
    val randomIdx = (math.random * webSites.length).toInt
    webSites(randomIdx)
  }
}
