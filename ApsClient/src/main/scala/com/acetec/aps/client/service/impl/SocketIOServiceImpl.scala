package com.acetec.aps.client.service.impl

import com.acetec.aps.client.service.{ErrorCode, SocketIOService}
import com.acetec.aps.share._
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.google.inject.Singleton
import io.socket.client.{IO, Socket}
import org.json.JSONObject
import rx.lang.scala.Observable
import rx.subjects.PublishSubject

import scala.reflect.{ClassTag, _}

@Singleton
class SocketIOServiceImpl extends SocketIOService {

  private val _connect = PublishSubject.create[Unit]
  private val _disconnect = PublishSubject.create[Unit]

  override def eventConnect: PublishSubject[Unit] = _connect

  override def eventDisconnect: PublishSubject[Unit] = _disconnect

  private val mapper = new ObjectMapper()
  mapper.registerModules(DefaultScalaModule, new JsonOrgModule)

  // Setup socket.io
  val socket: Socket = IO.socket("http://localhost:7964")
  socket
    .on(Socket.EVENT_CONNECT, (objects: AnyRef) => {
      onConnect(socket, objects)
    })
    .on(Socket.EVENT_DISCONNECT, (objects: AnyRef) => {
      onDisconnect(socket, objects)
    })

  socket.connect()

  def onConnect(socket: Socket, objects: AnyRef*): Unit = {
    _connect.onNext()
    printf("onConnect\n")
  }

  def onDisconnect(socket: Socket, objects: AnyRef*): Unit = {
    _disconnect.onNext()
    printf("onDisconnect\n")
  }

  // 利用ClassTag获取Class信息
  private def convertResp[T: ClassTag](objects: AnyRef): ResponseWrapper[T] = {
    val jsonObject = objects.asInstanceOf[Array[Object]].head.asInstanceOf[JSONObject]
    val errorCode = jsonObject.getInt("errorCode")
    val payload = mapper.convertValue(jsonObject.getJSONObject("payload"), classTag[T].runtimeClass)
    ResponseWrapper[T](errorCode, payload.asInstanceOf[T])
  }

  private def convertReq[T](request: T): JSONObject = {
    mapper.convertValue(request, classOf[JSONObject])
  }

  override def registerBroadcastHandler[T: ClassTag](name: String, publishSubject: PublishSubject[T]): Unit = {
    socket.on(s"broadcast/$name", (objects: AnyRef) => {
      printf(s"broadcast/$name\n")
      val resp = convertResp[T](objects)
      if (resp.errorCode == 0) {
        publishSubject.onNext(resp.payload)
      } else {
        publishSubject.onError(ErrorCode(resp.errorCode))
      }
    })
  }


  override def requestTemplate[REQ: ClassTag, RES: ClassTag](request: REQ, name: String): Observable[RES] = {
    requestTemplate[REQ, RES](request, name, null)
  }

  override def requestTemplate[REQ: ClassTag, RES: ClassTag](request: REQ,
                                                             name: String,
                                                             publishSubject: PublishSubject[RES]): Observable[RES] = {
    printf(s"request/$name\n")
    Observable[RES](
      observer => {
        socket.off(s"response/$name")
        socket.on(s"response/$name", (objects: AnyRef) => {
          printf(s"response/$name\n")
          val resp = convertResp[RES](objects)
          val errorCode = resp.errorCode
          val payload = resp.payload
          if (errorCode == 0) {
            observer.onNext(payload)
            if (publishSubject != null)
              publishSubject.onNext(payload)
          } else {
            observer.onError(ErrorCode(errorCode))
            if (publishSubject != null)
              publishSubject.onError(ErrorCode(errorCode))
          }
        })
        socket.emit(s"request/$name", convertReq(request))
      })
  }
}
