package com.acetec.aps.client.view

import com.acetec.aps.share.dto.LotDto

import scalafx.scene.control.Control


trait LotListView {
  def setLots(lots: List[LotDto])

  def content: Control
}


//object UnscheduledLotListView {
//
//  val model = new UnscheduledLotListModel()
//
//  def content = new TabPane {
//    tabs = model.data.map(m => new Tab {
//      text = m.name
//      closable = false
//      content = {

//    })
//  }
//}
