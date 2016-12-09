package com.acetec.aps.server.entity

import java.util
import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "TESTER_MODEL")
class TesterModel {

  @BeanProperty
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", unique = true, nullable = false)
  var id: Long = _

  @BeanProperty
  @Column(name = "NAME", /*unique = true, */ nullable = false)
  var name: String = _

  @BeanProperty
  @OneToMany(cascade = Array(CascadeType.ALL), mappedBy = "testerModel", fetch = FetchType.EAGER)
  var testerPlatforms: util.List[TesterPlatform] = _

  @BeanProperty
  @OneToMany(cascade = Array(CascadeType.ALL), mappedBy = "testerModel")
  var lots: util.List[Lot] = _
}
