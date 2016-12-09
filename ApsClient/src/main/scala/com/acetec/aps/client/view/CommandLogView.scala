package com.acetec.aps.client.view

import scalafx.scene.layout.Region

trait CommandLogView {

  def content: Region

  def showJoin(): Unit

  def showLeave(): Unit

  def hideLeave(): Unit

  def hideJoin(): Unit

  def appendLog(log: String): Unit
}

