package com.acetec.aps.client.presenter

import com.acetec.aps.client.service.LoginService
import com.acetec.aps.client.view.LoginView
import com.acetec.aps.share.{LoginResponse, LoginResultCode}
import com.google.inject.Inject

class LoginPresenterImpl @Inject()(view: LoginView, loginService: LoginService) extends LoginPresenter {

  override def validateCredentials(username: String, password: String): Unit = {
    if (view != null)
      view.showProgress()
    loginService.login(username, password)
      .subscribe((resp: LoginResponse) => {
        resp.resultCode match {
          case LoginResultCode.Success =>
            view.hideProgress()
            view.hide()
            return
          case LoginResultCode.UsernameError =>
            view.hideProgress()
            view.setUsernameError()
            return
          case LoginResultCode.PasswordError =>
            view.hideProgress()
            view.setPasswordError()
          case LoginResultCode.LoggedInError =>
            view.hideProgress()
            view.setLoggedInError()
            return
        }
      }, (ex) => {

      })
  }
}
