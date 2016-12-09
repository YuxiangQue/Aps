package com.acetec.aps.server.entity

import java.util
import javax.persistence.{Column, _}

import scala.beans.BeanProperty

@Entity
@Table(name = "USER")
class User {

  @BeanProperty
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", unique = true, nullable = false)
  var id = 0L

  @BeanProperty
  @Column(name = "USERNAME", unique = true)
  var username = ""

  @BeanProperty
  @Column(name = "PASSWORD")
  var password = ""

  @BeanProperty
  @Column(name = "SALT")
  var salt = ""

  @BeanProperty
  @Column(name = "DISABLED")
  var disabled = false

  @BeanProperty
  @OneToMany(cascade = Array(CascadeType.ALL), mappedBy = "user")
  var roles: util.Set[Role] = _
}
