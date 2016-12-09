package com.acetec.aps.client.service

import com.acetec.aps.share.dto.ProductDto
import rx.lang.scala.Observable

trait ProductService {
  def findAllProducts(): Observable[List[ProductDto]]

}




