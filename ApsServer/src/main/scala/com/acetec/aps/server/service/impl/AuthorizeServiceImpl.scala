package com.acetec.aps.server.service.impl

import com.acetec.aps.server.common.aspect.{Loggable, ResourceRequired}
import com.acetec.aps.server.dao.{PermissionDao, RoleDao, UserDao}
import com.acetec.aps.server.entity.{Permission, Role, User}
import com.acetec.aps.server.service.AuthorizeService
import com.acetec.aps.share.dto.{PermissionDto, RoleDto, UserDto}
import ma.glasnost.orika.MapperFacade
import org.springframework.beans.factory.annotation.Autowired

import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer


class AuthorizeServiceImpl extends AuthorizeService {
  @Autowired
  private var userDao: UserDao = _
  @Autowired
  private var roleDao: RoleDao = _
  @Autowired
  private var permissionDao: PermissionDao = _
  @Autowired
  private var modelMapper: MapperFacade = _

  @ResourceRequired("user-list")
  @Loggable
  def findAllUser(jwtToken: String): List[UserDto] = {
    val dtos = new ListBuffer[UserDto]
    for (entity <- userDao.findAll) dtos.append(modelMapper.map(entity, classOf[UserDto]))
    dtos.toList
  }

  @ResourceRequired("role-list")
  @Loggable
  def findAllRole(jwtToken: String): List[RoleDto] = {
    val dtos = new ListBuffer[RoleDto]
    for (entity <- roleDao.findAll) dtos.append(modelMapper.map(entity, classOf[RoleDto]))
    dtos.toList
  }

  @ResourceRequired("resource-list")
  @Loggable
  def findAllResource(jwtToken: String): List[PermissionDto] = {
    val dtos = new ListBuffer[PermissionDto]
    import scala.collection.JavaConversions._
    for (entity <- permissionDao.findAll) {
      dtos.add(modelMapper.map(entity, classOf[PermissionDto]))
    }
    dtos.toList
  }

  @ResourceRequired("user-add")
  @Loggable def saveUser(jwtToken: String, userDto: UserDto): Boolean = {
    var entity = userDao.findByUsername(userDto.username)
    if (entity != null) throw new IllegalArgumentException("username")
    entity = modelMapper.map(userDto, classOf[User])
    val roles = if (entity.roles == null) Set.empty[Role]
    else entity.roles.map(r => r.name)
      .map(name => roleDao.findByName(name))
      .filter(r => r != null)
      .toSet
    entity.roles = roles
    entity.salt = ""
    entity.disabled = false
    userDao.save(entity)
    true
  }

  @ResourceRequired("role-add")
  @Loggable def saveRole(jwtToken: String, roleDto: RoleDto): Boolean = {
    var entity = roleDao.findByName(roleDto.name)
    if (entity != null) throw new IllegalArgumentException("name")
    entity = modelMapper.map(roleDto, classOf[Role])
    val permissions = if (entity.permissions == null) Set.empty[Permission]
    else entity.permissions
      .map(p => p.name)
      .map(name => permissionDao.findByName(name))
      .filter(r => r != null)
      .toSet
    entity.permissions = permissions
    roleDao.save(entity)
    true
  }

  @ResourceRequired("resource-add")
  @Loggable def saveResource(jwtToken: String, permissionDto: PermissionDto): Boolean = {
    var entity = permissionDao.findByName(permissionDto.name)
    if (entity != null) throw new IllegalArgumentException("name")
    entity = modelMapper.map(permissionDto, classOf[Permission])
    permissionDao.save(entity)
    true
  }

  @ResourceRequired("user-list")
  @Loggable
  def findUserByUsername(jwtToken: String, username: String): Nothing = {
    val entity = userDao.findByUsername(username)
    if (entity == null) throw new IllegalArgumentException("username")
    modelMapper.map(entity, classOf[Nothing])
  }

  @ResourceRequired("role-list")
  @Loggable
  def findRoleByName(jwtToken: String, name: String): Nothing = {
    val entity = roleDao.findByName(name)
    if (entity == null) throw new IllegalArgumentException("name")
    modelMapper.map(entity, classOf[Nothing])
  }

  @ResourceRequired("role-list")
  @Loggable def findResourceByName(jwtToken: String, name: String): Nothing = {
    val entity = permissionDao.findByName(name)
    if (entity == null) throw new IllegalArgumentException("name")
    modelMapper.map(entity, classOf[Nothing])
  }

  @ResourceRequired("user-edit")
  @Loggable
  def updateUserPassword(jwtToken: String, dto: UserDto): Boolean = {
    val entity = userDao.findByUsername(dto.username)
    if (entity == null) throw new IllegalArgumentException("username")
    entity.password = dto.password
    userDao.save(entity)
    true
  }

  @ResourceRequired("user-edit")
  @Loggable
  def updateUser(jwtToken: String, dto: UserDto): Boolean = {
    val entity = userDao.findByUsername(dto.username)
    if (entity == null) throw new IllegalArgumentException("username")
    val roleNames = dto.roles.map(r => r.name).toSet
    val roles = roleNames.map(n => roleDao.findByName(n)).filter(r => r != null).toSet
    entity.roles = roles
    entity.disabled = dto.disabled
    userDao.save(entity)
    true
  }

  @ResourceRequired("user-edit")
  @Loggable
  def changePassword(jwtToken: String, username: String, newPassword: String): Boolean = {
    val entity = userDao.findByUsername(username)
    if (entity == null) throw new IllegalArgumentException("username")
    entity.password = newPassword
    userDao.save(entity)
    true
  }

  def updatePermission(jwtToken: String, dto: PermissionDto): Boolean = {
    val entity = permissionDao.findByName(dto.name)
    if (entity == null) throw new IllegalArgumentException("username")
    entity.description = dto.description
    entity.displayName = dto.displayName
    permissionDao.save(entity)
    true
  }

  @ResourceRequired("role-edit")
  @Loggable
  def updateRole(jwtToken: String, dto: RoleDto): Boolean = {
    val entity = roleDao.findByName(dto.name)
    if (entity == null) throw new IllegalArgumentException("username")
    val roleNames = dto.permissions.map(p => p.name).toSet
    val permissions = roleNames.map(n => permissionDao.findByName(n))
      .filter(r => r != null).toSet
    entity.description = dto.description
    entity.displayName = dto.displayName
    entity.permissions = permissions
    roleDao.save(entity)
    true
  }

  @ResourceRequired("permission-list")
  @Loggable
  def hasResource(jwtToken: String, username: String, permissionName: String): Boolean = {
    val entity = userDao.findByUsername(username)
    if (entity == null) throw new IllegalArgumentException("username")
    for (role <- entity.roles) {
      for (resource <- role.permissions) {
        if (resource.name == permissionName) return true
      }
    }
    false
  }

  @ResourceRequired("user-remove")
  @Loggable
  def deleteUserByUsername(jwtToken: String, username: String): Boolean = {
    val entity = userDao.findByUsername(username)
    if (entity == null) throw new IllegalArgumentException("username")
    userDao.delete(entity)
    true
  }

  @ResourceRequired("user-remove")
  @Loggable
  def deleteRoleByName(jwtToken: String, name: String): Boolean = {
    val entity = roleDao.findByName(name)
    if (entity == null) throw new IllegalArgumentException("name")
    roleDao.delete(entity)
    true
  }

  @ResourceRequired("user-remove")
  @Loggable
  def deleteResourceByName(jwtToken: String, name: String): Boolean = {
    val entity = permissionDao.findByName(name)
    if (entity == null) throw new IllegalArgumentException("name")
    permissionDao.delete(entity)
    true
  }
}
