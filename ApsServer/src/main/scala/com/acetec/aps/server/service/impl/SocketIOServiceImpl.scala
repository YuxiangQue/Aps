package com.acetec.aps.server.service.impl

import com.acetec.aps.server.entity.MemberState
import com.acetec.aps.server.service.SocketIOService
import com.acetec.aps.share._
import com.corundumstudio.socketio.annotation.{OnConnect, OnDisconnect, OnEvent}
import com.corundumstudio.socketio.{AckRequest, SocketIOClient, SocketIOServer}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SocketIOServiceImpl extends SocketIOService {
  private val logger = LoggerFactory.getLogger(classOf[SocketIOServiceImpl])

  @Autowired
  private var memberService: MemberServiceImpl = _

  @Autowired
  private var server: SocketIOServer = _

  @OnConnect
  def onConnect(client: SocketIOClient) {
    logger.info("onConnect " + client)
  }

  @OnDisconnect
  def onDisconnect(client: SocketIOClient) {
    logger.info("onDisconnect" + client)
  }

  @OnEvent(value = "request/login")
  def onLogin(client: SocketIOClient, request: AckRequest, login: LoginRequest): Unit = {
    logger.info("request/login")
    if (!memberService.contains(client)) {
      memberService.login(login.username, client)
      client.sendEvent("response/login", ResponseWrapper(0, LoginResponse(LoginResultCode.Success)))
    } else {
      client.sendEvent("response/login", ResponseWrapper(0, LoginResponse(LoginResultCode.LoggedInError)))
    }
  }

  @OnEvent(value = "request/join")
  def onJoin(client: SocketIOClient, request: AckRequest, req: JoinRequest): Unit = {
    logger.info("request/join")
    if (memberService.contains(client)) {
      val member = memberService.get(client)
      if (member.state == MemberState.Left) {
        memberService.join(client)
        val username = memberService.usernameOf(client)
        val usernames = memberService.usernameOfAll()
        server.getBroadcastOperations.sendEvent("broadcast/join",
          client, // exclude self
          ResponseWrapper(0, JoinBroadcast(username, usernames)))
        client.sendEvent("response/join", ResponseWrapper(0, JoinResponse(username, usernames)))
      }
    }
  }

  @OnEvent(value = "request/leave")
  def onLeave(client: SocketIOClient, request: AckRequest, req: LeaveRequest): Unit = {
    logger.info("request/leave")
    if (memberService.contains(client)) {
      val member = memberService.get(client)
      if (member.state == MemberState.Joined) {
        memberService.leave(client)
        val username = memberService.usernameOf(client)
        val usernames = memberService.usernameOfAll()
        server.getBroadcastOperations.sendEvent("broadcast/leave",
          client, // exclude self
          ResponseWrapper(0, LeaveBroadcast(username, usernames)))
        client.sendEvent("response/leave", ResponseWrapper(0, LeaveResponse(username, usernames)))
      }
    }
  }

  @OnEvent(value = "request/chat")
  def onChat(client: SocketIOClient, requset: AckRequest, req: ChatRequest): Unit = {
    logger.info("request/chat")
    if (memberService.contains(client)) {
      val member = memberService.get(client)
      if (member.state == MemberState.Joined) {
        server.getBroadcastOperations.sendEvent("broadcast/chat",
          client,
          ResponseWrapper(0, ChatBroadcast(member.username, req.message)))
        client.sendEvent("response/chat", ResponseWrapper(0, ChatResponse(member.username, req.message)))
      }
    }
  }
}
