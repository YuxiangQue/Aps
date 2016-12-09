package com.acetec.aps.client.presenter

import com.acetec.aps.client.service.{LoginService, TesterModelService}
import com.acetec.aps.client.view.TesterModelTreeView
import com.google.inject.Inject


class TesterModelPresenterImpl @Inject()(view: TesterModelTreeView,
                                         loginService: LoginService,
                                         testerModelService: TesterModelService) extends TesterModelPresenter {

  loginService.loginResponse.subscribe(resp => {
    getTesterModels()
  })

  override def getTesterModels(): Unit = {
    testerModelService.findAllTesterModels().subscribe(testerModels => {
      view.setTesterModels(testerModels)
    })
  }
}
