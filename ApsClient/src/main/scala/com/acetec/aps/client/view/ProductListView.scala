package com.acetec.aps.client.view

import com.acetec.aps.share.dto.ProductDto

import scalafx.scene.control.Control

trait ProductListView {

  def content: Control

  def setProducts(products: List[ProductDto])

}

