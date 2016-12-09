package com.acetec.aps.client.view

import com.acetec.aps.client.model.LotModel
import com.acetec.aps.client.presenter.LotListPresenter
import com.acetec.aps.share.dto.LotDto
import com.google.inject.Inject

import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{TableColumn, TableView}

class LotListViewImpl @Inject()(presenter: LotListPresenter) extends LotListView {

  val lotObservableBuffer = new ObservableBuffer[LotModel]()

  override def content = createTableView()

  def createTableView(): TableView[LotModel] = {
    // Create columns
    val lotNumberColumn = new TableColumn[LotModel, String] {
      text = "LotNumber"
      cellValueFactory = {
        _.value.lotNumber
      }
    }
    val uniquePNColumn = new TableColumn[LotModel, String] {
      text = "UniquePN"
      cellValueFactory = {
        _.value.uniquePN
      }
    }
    val customerColumn = new TableColumn[LotModel, String] {
      text = "Customer"
      cellValueFactory = {
        _.value.customer
      }
    }
    val customerPnColumn = new TableColumn[LotModel, String] {
      text = "CustomerPN"
      cellValueFactory = {
        _.value.customerPN
      }
    }
    val processColumn = new TableColumn[LotModel, String] {
      text = "Process"
      cellValueFactory = {
        _.value.process
      }
    }
    val testerModelColumn = new TableColumn[LotModel, String] {
      text = "TesterModel"
      cellValueFactory = {
        _.value.testerModel
      }
    }
    val hr2UsdColumn = new TableColumn[LotModel, String] {
      text = "HR2USD"
      cellValueFactory = {
        _.value.hr2Usd
      }
    }
    val lotSizeColumn = new TableColumn[LotModel, Int] {
      text = "LotSize"
      cellValueFactory = {
        _.value.lotSize
      }
    }
    // Create table
    val table = new TableView[LotModel]() {
      columns += (lotNumberColumn,
        uniquePNColumn,
        customerColumn,
        customerPnColumn,
        processColumn,
        testerModelColumn,
        hr2UsdColumn
      )
      items = lotObservableBuffer
    }
    // Listen to row selection, and print values of the selected row
    table.selectionModel().selectedItem.onChange(
      (_, _, newValue) => println(newValue + " chosen in TableView")
    )
    table
  }

  override def setLots(lots: List[LotDto]): Unit = {
    lotObservableBuffer.clear()
    lotObservableBuffer.appendAll(lots.map(LotModel))
  }
}

