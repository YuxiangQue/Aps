package com.acetec.aps.server.entity

import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "RND") class Rnd {

  @BeanProperty
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", unique = true, nullable = false)
  var id: Long = _

  @BeanProperty
  @Column(name = "FIRST_LOT_PLACE", unique = false, nullable = false)
  var firstLotPlace: Double = _

  @BeanProperty
  @Column(name = "LOT_SIZE_PCNT", unique = false, nullable = false)
  var lotSizePcnt: Double = _

  @BeanProperty
  @Column(name = "CPU_TEST_TIME_PCNT", unique = false, nullable = false)
  var cpuTestTimePcnt: Double = _

  @BeanProperty
  @Column(name = "YIELD", unique = false, nullable = false)
  var yield1: Double = _

  @BeanProperty
  @Column(name = "TAIL_TEST_END_TO_START", unique = false, nullable = false)
  var tailTestEndToStart: Double = _

  @BeanProperty
  @Column(name = "TAIL_INCOMING", unique = false, nullable = false)
  var tailIncoming: Double = _

  @BeanProperty
  @Column(name = "TAIL_LOT_CREATE", unique = false, nullable = false)
  var tailLotCreate: Double = _

  @BeanProperty
  @Column(name = "TAIL_TEST_END", unique = false, nullable = false)
  var tailTestEnd: Double = _

  @BeanProperty
  @Column(name = "PT1_TIME_PCNT", unique = false, nullable = false)
  var pt1TimePcnt: Double = _

  @BeanProperty
  @Column(name = "TAIL_PT1_END", unique = false, nullable = false)
  var tailPt1End: Double = _

  @BeanProperty
  @Column(name = "PT2_TIME_PCNT", unique = false, nullable = false)
  var tailPt2Start: Double = _

  @BeanProperty
  @Column(name = "TAIL_PT2_END", unique = false, nullable = false)
  var tailPt2End: Double = _

  @BeanProperty
  @Column(name = "TAIL_MOVE_OUT", unique = false, nullable = false)
  var tailMoveOut: Double = _
}
