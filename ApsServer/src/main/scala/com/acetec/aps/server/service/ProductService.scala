package com.acetec.aps.server.service

import com.acetec.aps.share.dto.ProductDto

trait ProductService {
  def deleteAllProducts(): Unit

  def findProductByUniquePn(uniquePn: String): ProductDto

  def saveProduct(product: ProductDto): ProductDto

  def findAllProducts(): List[ProductDto]
}

