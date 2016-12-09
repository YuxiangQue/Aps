package com.acetec.aps.client.presenter

import com.acetec.aps.client.service.{LoginService, ProductService}
import com.acetec.aps.client.view.ProductListView
import com.google.inject.Inject

class ProductListPresenterImpl @Inject()(view: ProductListView,
                                         loginService: LoginService,
                                         productService: ProductService) extends ProductListPresenter {

  loginService.loginResponse.subscribe(resp => {
    getProducts()
  })

  override def getProducts(): Unit = {
    productService.findAllProducts().subscribe(products => {
      view.setProducts(products)
    })
  }
}
