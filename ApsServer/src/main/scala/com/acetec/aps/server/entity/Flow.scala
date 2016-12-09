package com.acetec.aps.server.entity

import java.util.Date
import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "FLOW")
class Flow {

  @BeanProperty
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", unique = true, nullable = false)
  var id: Long = _

  @BeanProperty
  @Column(name = "INCOMING_DATE")
  var incomingDate: Date = _

  @BeanProperty
  @Column(name = "MOVE_IN_DATE")
  var moveInDate: Date = _

  @BeanProperty
  @Column(name = "LOT_CREATE_DATE")
  var lotCreateDate: Date = _

  @BeanProperty
  @Column(name = "TEST_START_DATE")
  var testStartDate: Date = _

  @BeanProperty
  @Column(name = "TEST_END_DATE")
  var testEndDate: Date = _

  @BeanProperty
  @Column(name = "PT1_START_DATE")
  var pt1StartDate: Date = _

  @BeanProperty
  @Column(name = "PT1_END_DATE")
  var pt1EndDate: Date = _

  @BeanProperty
  @Column(name = "PT2_START_DATE")
  var pt2StartDate: Date = _

  @BeanProperty
  @Column(name = "PT2_END_DATE")
  var pt2EndDate: Date = _

  @BeanProperty
  @Column(name = "MOVE_OUT_DATE")
  var moveOutDate: Date = _

  @BeanProperty
  @OneToOne(mappedBy = "flow")
  var lot: Lot = _
}
