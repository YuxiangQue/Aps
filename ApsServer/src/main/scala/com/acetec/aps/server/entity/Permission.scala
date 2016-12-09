package com.acetec.aps.server.entity

import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "PERMISSION")
class Permission(_name: String) {

  @BeanProperty
  @Column(name = "NAME", unique = true, nullable = false)
  var name: String = _

  @BeanProperty
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", unique = true, nullable = false)
  var id: Long = _

  @BeanProperty
  @ManyToOne(cascade = Array(CascadeType.ALL))
  @JoinColumn(name = "ROLE_ID")
  var role: Role = _

  @BeanProperty
  @Column(name = "DISPLAY_NAME")
  var displayName = ""

  @BeanProperty
  @Column(name = "DESCRIPTION")
  var description = ""

  name = _name
}
