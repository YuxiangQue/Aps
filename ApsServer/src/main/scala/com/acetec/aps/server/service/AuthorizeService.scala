package com.acetec.aps.server.service


import com.acetec.aps.share.dto.{PermissionDto, RoleDto, UserDto}

trait AuthorizeService {
  def findAllUser(jwtToken: String): List[UserDto]

  def findAllRole(jwtToken: String): List[RoleDto]

  def findAllResource(jwtToken: String): List[PermissionDto]

  def saveUser(jwtToken: String, user: UserDto): Boolean

  def saveRole(jwtToken: String, role: RoleDto): Boolean

  def saveResource(jwtToken: String, resource: PermissionDto): Boolean

  def findUserByUsername(jwtToken: String, username: String): UserDto

  def findRoleByName(jwtToken: String, name: String): RoleDto

  def findResourceByName(jwtToken: String, name: String): PermissionDto

  def updateUserPassword(jwtToken: String, dto: UserDto): Boolean

  def updateUser(jwtToken: String, dto: UserDto): Boolean

  def updateRole(jwtToken: String, dto: RoleDto): Boolean

  def hasResource(jwtToken: String, username: String, resourceName: String): Boolean

  def deleteUserByUsername(jwtToken: String, username: String): Boolean

  def deleteRoleByName(jwtToken: String, name: String): Boolean

  def deleteResourceByName(jwtToken: String, name: String): Boolean

  def changePassword(jwtToken: String, username: String, newPassword: String): Boolean

  def updatePermission(jwtToken: String, resourceDto: PermissionDto): Boolean
}
