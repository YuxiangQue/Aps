package com.acetec.aps.share.dto

case class RoleDto() {
  var name: String = _
  var permissions: List[PermissionDto] = _
  var description: String = _
  var displayName: String = _
}
