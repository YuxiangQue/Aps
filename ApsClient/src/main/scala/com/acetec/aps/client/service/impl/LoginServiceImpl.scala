package com.acetec.aps.client.service.impl

import com.acetec.aps.client.service.{LoginService, SocketIOService}
import com.acetec.aps.share.{LoginRequest, LoginResponse, LoginResultCode}
import com.google.inject.{Inject, Singleton}
import rx.lang.scala.Observable
import rx.subjects.PublishSubject

@Singleton
class LoginServiceImpl @Inject()(socketIOService: SocketIOService) extends LoginService {

  private var _me: String = _

  private val _loginResponse = PublishSubject.create[LoginResponse]()

  override def loginResponse: PublishSubject[LoginResponse] = _loginResponse

  override def login(username: String, password: String): Observable[LoginResponse] = {
    socketIOService.requestTemplate(LoginRequest(username, password), "login", _loginResponse).map(lr => {
      if (lr.resultCode == LoginResultCode.Success) {
        _me = username
      }
      lr
    })
  }

  override def me(): Observable[String] = {
    Observable.just(_me)
  }
}
