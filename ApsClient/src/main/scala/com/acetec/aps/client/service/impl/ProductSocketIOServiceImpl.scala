package com.acetec.aps.client.service.impl

import com.acetec.aps.client.service.{ProductService, SocketIOService}
import com.acetec.aps.share.FindAllProducts
import com.acetec.aps.share.dto.ProductDto
import com.acetec.aps.share.service.TemplateService
import com.google.inject.Inject
import rx.lang.scala.Observable

class ProductSocketIOServiceImpl @Inject()(templateService: TemplateService,
                                           socketIOService: SocketIOService) extends ProductService {


  override def findAllProducts(): Observable[List[ProductDto]] = {
    socketIOService.requestTemplate[FindAllProducts.Request, FindAllProducts.Response](null, FindAllProducts.name).map(resp => {
      resp.products
    })
  }
}
