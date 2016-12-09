package com.acetec.aps.client.view

import com.acetec.aps.share.dto.TesterModelDto

import scalafx.scene.control.Control

trait TesterModelTreeView {
  def content: Control

  def setTesterModels(testerModels: List[TesterModelDto]): Unit
}
