package com.acetec.aps.server.entity

import java.util
import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "TESTER_PLATFORM") class TesterPlatform {

  @BeanProperty
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", unique = true, nullable = false)
  var id: Long = _

  @BeanProperty
  @Column(name = "NAME", /* unique = true, */ nullable = false)
  var name: String = _

  @BeanProperty
  @OneToMany(cascade = Array(CascadeType.ALL), mappedBy = "testerPlatform", fetch = FetchType.EAGER)
  var testers: util.List[Tester] = _

  @BeanProperty
  @ManyToOne(cascade = Array(CascadeType.ALL))
  @JoinColumn(name = "TESTER_MODEL_ID")
  var testerModel: TesterModel = _
}
