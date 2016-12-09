package com.acetec.aps.client.service

import com.acetec.aps.share._
import rx.lang.scala.Observable
import rx.subjects.PublishSubject

trait CooperateSocketIOService {
  def joinBroadcast: PublishSubject[JoinBroadcast]

  def leaveBroadcast: PublishSubject[LeaveBroadcast]

  def chatBroadcast: PublishSubject[ChatBroadcast]

  def join(request: JoinRequest): Observable[JoinResponse]

  def leave(request: LeaveRequest): Observable[LeaveResponse]

  def chat(request: ChatRequest): Observable[ChatResponse]
}
