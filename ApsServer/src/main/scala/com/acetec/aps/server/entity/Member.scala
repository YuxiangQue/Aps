package com.acetec.aps.server.entity

import com.corundumstudio.socketio.SocketIOClient

class Member(val username: String, val client: SocketIOClient) {
  var state = MemberState.Left
}
