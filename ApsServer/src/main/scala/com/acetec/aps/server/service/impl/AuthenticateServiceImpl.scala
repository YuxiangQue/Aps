package com.acetec.aps.server.service.impl

import com.acetec.aps.server.Application
import com.acetec.aps.server.common.aspect.Loggable
import com.acetec.aps.server.dao.{PermissionDao, RoleDao, UserDao}
import com.acetec.aps.server.entity.User
import com.acetec.aps.server.exception.{DisabledAccountException, IncorrectPasswordException}
import com.acetec.aps.server.service.AuthenticateService
import io.jsonwebtoken.{Jwts, SignatureAlgorithm}
import ma.glasnost.orika.MapperFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service class AuthenticateServiceImpl extends AuthenticateService {
  @Autowired private var userDao: UserDao = _
  @Autowired private var roleDao: RoleDao = _
  @Autowired private var permissionDao: PermissionDao = _
  @Autowired private var modelMapper: MapperFactory = _

  @Loggable def authenticate(username: String, password: String): String = {
    val user = userDao.findByUsername(username)
    if (user == null) throw new IncorrectPasswordException
    if (user.getDisabled) throw new DisabledAccountException
    if (user.getPassword != password) throw new IncorrectPasswordException
    getToken(user)
  }

  def getUsername(token: String): String = {
    val claimsJws = Jwts.parser.setSigningKey(Application.secret).parseClaimsJws(token)
    val username = claimsJws.getBody.getSubject
    username
  }

  def getToken(user: User): String = Jwts.builder.setSubject(user.getUsername).signWith(SignatureAlgorithm.HS512, Application.secret).compact
}
