package com.acetec.aps.client.service.impl

import com.acetec.aps.client.service.{CooperateSocketIOService, SocketIOService}
import com.acetec.aps.share._
import com.google.inject.Inject
import rx.lang.scala.Observable
import rx.subjects.PublishSubject

class CooperateSocketIOServiceImpl @Inject()(socketIOService: SocketIOService) extends CooperateSocketIOService {

  private val _joinBroadcast = PublishSubject.create[JoinBroadcast]()
  private val _leaveBroadcast = PublishSubject.create[LeaveBroadcast]()
  private val _chatBroadcast = PublishSubject.create[ChatBroadcast]()

  override def joinBroadcast: PublishSubject[JoinBroadcast] = _joinBroadcast

  override def leaveBroadcast: PublishSubject[LeaveBroadcast] = _leaveBroadcast

  override def chatBroadcast: PublishSubject[ChatBroadcast] = _chatBroadcast

  socketIOService.registerBroadcastHandler("join", _joinBroadcast)
  socketIOService.registerBroadcastHandler("leave", _leaveBroadcast)
  socketIOService.registerBroadcastHandler("chat", _chatBroadcast)

  override def join(request: JoinRequest): Observable[JoinResponse] = {
    socketIOService.requestTemplate[JoinRequest, JoinResponse](request, "join")
  }

  override def leave(request: LeaveRequest): Observable[LeaveResponse] = {
    socketIOService.requestTemplate[LeaveRequest, LeaveResponse](request, "leave")
  }

  override def chat(request: ChatRequest): Observable[ChatResponse] = {
    socketIOService.requestTemplate[ChatRequest, ChatResponse](request, "chat")
  }
}
