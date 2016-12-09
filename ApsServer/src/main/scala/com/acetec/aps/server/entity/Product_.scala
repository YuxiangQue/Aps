package com.acetec.aps.server.entity

import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "PRODUCT")
class Product_ {

  @BeanProperty
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", unique = true, nullable = false)
  var id: Long = _

  @BeanProperty
  @Column(name = "UNIQUE_PN")
  var uniquePn: String = _

  @BeanProperty
  @Column(name = "CUSTOMER_PN")
  var customerPn: String = _

  @BeanProperty
  @Column(name = "PROCESS")
  var process: String = _

  @BeanProperty
  @Column(name = "LOT_SIZE")
  var lotSize: Int = _

  @BeanProperty
  @Column(name = "CUSTOMER")
  var customer: String = _
}
