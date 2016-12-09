package com.acetec.aps.server.service.impl

import com.acetec.aps.server.dao.ProductDao
import com.acetec.aps.server.entity.Product_
import com.acetec.aps.server.service.ProductService
import com.acetec.aps.share.dto.ProductDto
import ma.glasnost.orika.MapperFacade
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.JavaConversions._

@Service
class ProductServiceImpl extends ProductService {

  @Autowired
  val productDao: ProductDao = null

  @Autowired
  val mapper: MapperFacade = null

  override def findProductByUniquePn(uniquePN: String): ProductDto = {
    val product = productDao.findByUniquePn(uniquePN)
    mapper.map(product, classOf[ProductDto])
  }

  override def saveProduct(product: ProductDto): ProductDto = {
    val entity = mapper.map(product, classOf[Product_])
    productDao.save(entity)
    mapper.map(entity, classOf[ProductDto])
  }

  override def findAllProducts(): List[ProductDto] = {
    val products = productDao.findAll()
    products.map(p => mapper.map(p, classOf[ProductDto])).toList
  }

  override def deleteAllProducts(): Unit = {
    productDao.deleteAll()
  }
}
