package com.acetec.aps.server.entity

import java.util
import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "TESTER")
class Tester {

  @BeanProperty
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", unique = true, nullable = false)
  var id: Long = _

  @BeanProperty
  @Column(name = "NAME", /*unique = true, */ nullable = false)
  var name: String = _

  @BeanProperty
  @OneToMany(cascade = Array(CascadeType.ALL), mappedBy = "tester", fetch = FetchType.EAGER)
  var lots: util.List[Lot] = _

  @BeanProperty
  @OneToOne(cascade = Array(CascadeType.ALL))
  @JoinColumn(name = "RND_ID")
  var rnd: Rnd = _

  @BeanProperty
  @ManyToOne(cascade = Array(CascadeType.ALL))
  @JoinColumn(name = "PLATFORM_ID")
  var testerPlatform: TesterPlatform = _
}
