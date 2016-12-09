package com.acetec.aps.server.service.impl

import com.acetec.aps.server.entity.{Member, MemberState}
import com.acetec.aps.server.service.MemberService
import com.corundumstudio.socketio.SocketIOClient
import org.springframework.stereotype.Service

import scala.collection.mutable

@Service
class MemberServiceImpl extends MemberService {
  def get(client: SocketIOClient): Member = {
    usernameToMember(clientToUsername(client))
  }

  private val usernameToMember = new mutable.HashMap[String, Member]()
  private val clientToUsername = new mutable.HashMap[SocketIOClient, String]()

  def contains(client: SocketIOClient): Boolean = {
    clientToUsername.contains(client)
  }

  def login(username: String, socketIOClient: SocketIOClient) = {
    if (usernameToMember.contains(username)) {
      val c = usernameToMember(username)
      c.client.disconnect()
      usernameToMember.remove(username)
      clientToUsername.remove(c.client)
    }
    usernameToMember(username) = new Member(username, socketIOClient)
    clientToUsername(socketIOClient) = username
  }

  def leave(client: SocketIOClient) = {
    val member = memberOf(client)
    member.state = MemberState.Left
  }

  def join(client: SocketIOClient) = {
    val member = memberOf(client)
    member.state = MemberState.Joined
  }

  def memberOf(client: SocketIOClient): Member = {
    usernameToMember(clientToUsername(client))
  }

  def usernameOf(client: SocketIOClient): String = {
    clientToUsername(client)
  }

  def usernameOfAll(): Seq[String] = {
    usernameToMember.keys.toSeq
  }
}
