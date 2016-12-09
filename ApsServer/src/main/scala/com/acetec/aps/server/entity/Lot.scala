package com.acetec.aps.server.entity

import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "LOT")
class Lot {

  @BeanProperty
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", unique = true, nullable = false)
  var id: Long = _

  @BeanProperty
  @OneToOne(cascade = Array(CascadeType.ALL))
  @JoinColumn(name = "RND_ID")
  var rnd: Rnd = _

  @BeanProperty
  @OneToOne(cascade = Array(CascadeType.ALL))
  @JoinColumn(name = "FLOW_ID")
  var flow: Flow = _

  @BeanProperty
  @Column(name = "TESTER_SN1")
  var testerSn1: String = _

  @BeanProperty
  @Column(name = "TESTER_SN2")
  var testerSn2: String = _

  @BeanProperty
  @Column(name = "BU_CODE1")
  var buCode1: String = _

  @BeanProperty
  @Column(name = "BU_CODE2")
  var buCode2: String = _

  @BeanProperty
  @Column(name = "UNIQUE_PN")
  var uniquePn: String = _

  @BeanProperty
  @Column(name = "LOT_NUMBER", unique = true)
  var lotNumber: String = _

  @BeanProperty
  @Column(name = "LOT_SIZE", unique = false)
  var lotSize: Int = _

  @BeanProperty
  @Column(name = "PASS_QTY", unique = false)
  var passQty: Int = _

  @BeanProperty
  @Column(name = "CPU_TEST_TIME_ACT", unique = false)
  var cpuTestTimeAct: Double = _

  @BeanProperty
  @Column(name = "YIELD", unique = false)
  var yield1: Double = _

  @BeanProperty
  @Column(name = "URGENT", unique = false)
  var isUrgent: Boolean = _

  @BeanProperty
  @Column(name = "HR2USD", unique = false)
  var hr2Usd: String = _

  @BeanProperty
  @ManyToOne(cascade = Array(CascadeType.ALL))
  @JoinColumn(name = "TESTER_ID")
  var tester: Tester = _

  @BeanProperty
  @ManyToOne(cascade = Array(CascadeType.ALL))
  @JoinColumn(name = "TESTER_MODEL_ID")
  var testerModel: TesterModel = _
}
