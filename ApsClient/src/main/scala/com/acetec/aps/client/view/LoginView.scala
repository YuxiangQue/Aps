package com.acetec.aps.client.view

import scalafx.stage.Window

trait LoginView {


  def showAndWait(owner: Window)

  def hide()

  def setLoggedInError()

  def showProgress()

  def hideProgress()

  def setUsernameError()

  def setPasswordError()
}
