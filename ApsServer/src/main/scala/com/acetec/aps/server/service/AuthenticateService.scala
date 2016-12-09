package com.acetec.aps.server.service

import com.acetec.aps.server.entity.User

trait AuthenticateService {
  def authenticate(username: String, password: String): String

  def getUsername(token: String): String

  def getToken(user: User): String
}

