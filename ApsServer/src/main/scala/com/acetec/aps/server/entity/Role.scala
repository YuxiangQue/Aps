package com.acetec.aps.server.entity

import java.util
import javax.persistence._

import scala.beans.BeanProperty


@Entity
@Table(name = "ROLE")
class Role(name_ : String) {

  @BeanProperty
  @Column(name = "NAME", unique = true, nullable = false)
  var name: String = name_

  @BeanProperty
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", unique = true, nullable = false)
  var id: Long = _

  @BeanProperty
  @Column(name = "DISPLAY_NAME")
  var displayName = ""

  @BeanProperty
  @Column(name = "DESCRIPTION")
  var description = ""

  @BeanProperty
  @OneToMany(cascade = Array(CascadeType.ALL), mappedBy = "role")
  var permissions: util.Set[Permission] = _

  @BeanProperty
  @ManyToOne(cascade = Array(CascadeType.ALL))
  @JoinColumn(name = "USER_ID")
  var user: User = _
}



