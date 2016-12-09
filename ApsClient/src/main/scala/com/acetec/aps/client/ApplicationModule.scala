package com.acetec.aps.client

import com.acetec.aps.client.presenter._
import com.acetec.aps.client.service._
import com.acetec.aps.client.service.impl._
import com.acetec.aps.client.view._
import com.acetec.aps.share.service.{TemplateService, TemplateServiceImpl}
import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule

class ApplicationModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    // Service
    bind[TemplateService].to[TemplateServiceImpl]
    bind[SocketIOService].to[SocketIOServiceImpl]
    bind[LoginService].to[LoginServiceImpl]
    bind[CooperateSocketIOService].to[CooperateSocketIOServiceImpl]
    bind[ProductService].to[ProductSocketIOServiceImpl]
    bind[LotService].to[LotSocketIOServiceImpl]
    bind[TesterModelService].to[TesterModelSocketIOServiceImpl]

    // View
    bind[LoginView].to[LoginViewImpl]
    bind[ProductListView].to[ProductListViewImpl]
    bind[RootView].to[RootViewImpl]
    bind[LotGanttView].to[LotGanttViewImpl]
    bind[LotListView].to[LotListViewImpl]
    bind[CommandLogView].to[CommandLogViewImpl]
    bind[TesterModelTreeView].to[TesterModelTreeViewImpl]

    // Presenter
    bind[LoginPresenter].to[LoginPresenterImpl]
    bind[ProductListPresenter].to[ProductListPresenterImpl]
    bind[RootPresenter].to[RootPresenterImpl]
    bind[LotGanttPresenter].to[LotGanttPresenterImpl]
    bind[LotListPresenter].to[LotListPresenterImpl]
    bind[CommandLogPresenter].to[CommandLogPresenterImpl]
    bind[TesterModelPresenter].to[TesterModelPresenterImpl]
  }
}
