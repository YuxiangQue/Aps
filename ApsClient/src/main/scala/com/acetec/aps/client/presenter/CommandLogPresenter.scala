package com.acetec.aps.client.presenter

trait CommandLogPresenter {
  def chat(message: String): Unit

  def leave()

  def join()
}
