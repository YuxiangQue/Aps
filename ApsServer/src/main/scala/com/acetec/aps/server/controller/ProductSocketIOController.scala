package com.acetec.aps.server.controller

import javax.annotation.PostConstruct

import com.acetec.aps.server.service.ProductService
import com.acetec.aps.share.{FindAllProducts, ResponseWrapper}
import com.corundumstudio.socketio.{AckRequest, SocketIOClient, SocketIOServer}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class ProductSocketIOController {

  private val logger = LoggerFactory.getLogger(classOf[ProductSocketIOController])

  @Autowired
  var productService: ProductService = _

  @Autowired
  private var server: SocketIOServer = _

  @PostConstruct
  def addEventListener(): Unit = {
    server.addEventListener(s"request/${FindAllProducts.name}", classOf[FindAllProducts.Request], (client: SocketIOClient, data: FindAllProducts.Request, ackSender: AckRequest) => {
      logger.info(s"request/${FindAllProducts.name}")
      val products = productService.findAllProducts()
      client.sendEvent(s"response/${FindAllProducts.name}", ResponseWrapper(0, FindAllProducts.Response(products)))
    })
  }
}
