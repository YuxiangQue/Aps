package com.acetec.aps.client.presenter

import com.acetec.aps.client.service.{LoginService, LotService}
import com.acetec.aps.client.view.LotListView
import com.google.inject.Inject

class LotListPresenterImpl @Inject()(view: LotListView,
                                     loginService: LoginService,
                                     lotService: LotService) extends LotListPresenter {

  loginService.loginResponse.subscribe(resp => {
    getLots()
  })

  override def getLots(): Unit = {
    lotService.findAllLots().subscribe(lots => {
      view.setLots(lots)
    })
  }
}
