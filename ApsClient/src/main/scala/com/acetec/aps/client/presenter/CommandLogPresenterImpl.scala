package com.acetec.aps.client.presenter

import com.acetec.aps.client.service.{CooperateSocketIOService, LoginService, SocketIOService}
import com.acetec.aps.client.view.CommandLogView
import com.acetec.aps.share.{ChatRequest, JoinRequest, LeaveRequest}
import com.google.inject.Inject

class CommandLogPresenterImpl @Inject()(view: CommandLogView,
                                        socketIOService: SocketIOService,
                                        loginService: LoginService,
                                        cooperateSocketIOService: CooperateSocketIOService) extends CommandLogPresenter {
  cooperateSocketIOService.joinBroadcast.subscribe(jb => {
    view.appendLog(s"[System]: ${jb.member} joined!")
  }, ex => {})

  cooperateSocketIOService.leaveBroadcast.subscribe(resp => {
    view.appendLog(s"[System]: ${resp.member} left!")
  }, ex => {})

  cooperateSocketIOService.chatBroadcast.subscribe(resp => {
    view.appendLog(s"${resp.sender}: ${resp.message}")
  }, ex => {})

  loginService.loginResponse.subscribe(resp => {
    view.showJoin()
  })

  override def chat(message: String): Unit = {
    cooperateSocketIOService.chat(ChatRequest(message)).subscribe(resp => {
      view.appendLog(s"${resp.sender}: ${resp.message}")
    })
  }

  override def leave(): Unit = {
    cooperateSocketIOService.leave(LeaveRequest()).subscribe(resp => {
      view.showJoin()
      view.hideLeave()
      view.appendLog(s"[System]: Goodbye, ${resp.member} !")
    }, ex => {

    })
  }

  override def join(): Unit = {
    cooperateSocketIOService.join(JoinRequest()).subscribe(resp => {
      view.hideJoin()
      view.showLeave()
      view.appendLog(s"[System]: Welcome, ${resp.member} !~")
    }, ex => {})
  }
}
