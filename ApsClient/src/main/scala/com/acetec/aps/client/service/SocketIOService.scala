package com.acetec.aps.client.service

import rx.lang.scala.Observable
import rx.subjects.PublishSubject

import scala.reflect.ClassTag


trait SocketIOService {

  def registerBroadcastHandler[T: ClassTag](name: String, publishSubject: PublishSubject[T]): Unit

  def requestTemplate[REQ: ClassTag, RES: ClassTag](request: REQ, name: String): Observable[RES]

  def requestTemplate[REQ: ClassTag, RES: ClassTag](request: REQ, name: String,
                                                    publishSubject: PublishSubject[RES]): Observable[RES]

  def eventConnect: PublishSubject[Unit]

  def eventDisconnect: PublishSubject[Unit]

}


