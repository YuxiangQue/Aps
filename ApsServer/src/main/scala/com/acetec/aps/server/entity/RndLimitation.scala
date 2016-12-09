package com.acetec.aps.server.entity

import javax.persistence._

@Entity
@Table(name = "RND_LIMITATION") class RndLimitation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", unique = true, nullable = false)
  var id: Long = _

  @Column(name = "FIRST_LOT_PLACE_LL", unique = false, nullable = false)
  var firstLotPlaceLL: Double = _

  @Column(name = "LOT_SIZE_PCNT_LL", unique = false, nullable = false)
  var lotSizePcntLL: Double = _

  @Column(name = "CPU_TEST_TIME_PCNT_LL", unique = false, nullable = false) var cpuTestTimePcntLL: Double = _

  @Column(name = "YIELD_LL", unique = false, nullable = false)
  var yieldLL: Double = _

  @Column(name = "TAIL_TEST_END_TO_START_LL", unique = false, nullable = false)
  var tailTestEndToStartLL: Double = _

  @Column(name = "TAIL_INCOMING_LL", unique = false, nullable = false)
  var tailIncomingLL: Double = _

  @Column(name = "TAIL_LOT_CREATE_LL", unique = false, nullable = false)
  var tailLotCreateLL: Double = _

  @Column(name = "TAIL_LOT_END_LL", unique = false, nullable = false)
  var tailTestEndLL: Double = _

  @Column(name = "PT1_TIME_PCNT_LL", unique = false, nullable = false)
  var pt1TimePcntLL: Double = _

  @Column(name = "TAIL_PT1_END_LL", unique = false, nullable = false)
  var tailPT1EndLL: Double = _

  @Column(name = "PT2_TIME_PCNT_LL", unique = false, nullable = false)
  var pt2TimePcntLL: Double = _

  @Column(name = "TAIL_PT2_END_LL", unique = false, nullable = false)
  var tailPT2EndLL: Double = _

  @Column(name = "TAIL_MOVE_OUT_LL", unique = false, nullable = false)
  var tailMoveOutLL: Double = _

  @Column(name = "FIRST_LOT_PLACE_UL", unique = false, nullable = false)
  var firstLotPlaceUL: Double = _

  @Column(name = "LOT_SIZE_PCNT_UL", unique = false, nullable = false)
  var lotSizePcntUL: Double = _

  @Column(name = "CPU_TEST_TIME_PCNT_UL", unique = false, nullable = false)
  var cpuTestTimePcntUL: Double = _

  @Column(name = "YIELD_UL", unique = false, nullable = false)
  var yieldUL: Double = _

  @Column(name = "TAIL_TEST_END_TO_START_UL", unique = false, nullable = false)
  var tailTestEndToStartUL: Double = _

  @Column(name = "TAIL_INCOMING_UL", unique = false, nullable = false)
  var tailIncomingUL: Double = _

  @Column(name = "TAIL_LOT_CREATE_UL", unique = false, nullable = false)
  var tailLotCreateUL: Double = _

  @Column(name = "TAIL_TEST_END_UL", unique = false, nullable = false)
  var tailTestEndUL: Double = _

  @Column(name = "PT1_TIME_PCNT_UL", unique = false, nullable = false)
  var pt1TimePcntUL: Double = _

  @Column(name = "TAIL_PT1_END_UL", unique = false, nullable = false)
  var tailPT1EndUL: Double = _

  @Column(name = "PT2_TIME_PCNT_UL", unique = false, nullable = false)
  var pt2TimePcntUL: Double = _

  @Column(name = "TAIL_PT2_END_UL", unique = false, nullable = false)
  var tailPT2EndUL: Double = _

  @Column(name = "TAIL_MOVE_OUT_UL", unique = false, nullable = false)
  var tailMoveOutUL: Double = _
}

