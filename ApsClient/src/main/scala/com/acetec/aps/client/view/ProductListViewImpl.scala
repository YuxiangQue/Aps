package com.acetec.aps.client.view

import com.acetec.aps.client.model.ProductModel
import com.acetec.aps.client.presenter.ProductListPresenter
import com.acetec.aps.share.dto.ProductDto
import com.google.inject.Inject

import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.scene.control.MenuItem._
import scalafx.scene.control._

class ProductListViewImpl @Inject()(presenter: ProductListPresenter) extends ProductListView {

  //  Observable.timer(1 second).subscribe(t => {
  //    presenter.getProducts()
  //  })

  val products = new ObservableBuffer[ProductModel]()

  val uniquePnCol = new TableColumn[ProductModel, String] {
    text = "UniquePN"
    cellValueFactory = {
      _.value.uniquePn
    }
  }

  val customerPnCol = new TableColumn[ProductModel, String] {
    text = "CustomerPN"
    cellValueFactory = {
      _.value.customerPn
    }
  }

  val processCol = new TableColumn[ProductModel, String] {
    text = "Process"
    cellValueFactory = {
      _.value.process
    }
  }

  val customerCol = new TableColumn[ProductModel, String] {
    text = "Customer"
    cellValueFactory = {
      _.value.customer
    }
  }

  val lotSizeCol = new TableColumn[ProductModel, Int] {
    text = "LotSize"
    cellValueFactory = {
      _.value.lotSize
    }
  }

  var content = new TableView[ProductModel]() {
    innerTable =>
    columns += (uniquePnCol, customerPnCol, processCol, customerCol, lotSizeCol)
    contextMenu = new ContextMenu {
      items += (new MenuItem("来料") {
        onAction = { e: ActionEvent => {
          val item = innerTable.selectionModel().getSelectedItem
          printf(s"来料 ${item.uniquePn}")
          var dialog = new TextInputDialog(defaultValue = "0") {
            title = "来料"
            headerText = "来料"
            contentText = "来料数量："
          }
          dialog.showAndWait()
        }
        }
      },
        new MenuItem("编辑") {
          onAction = { e: ActionEvent => {
            println(e.eventType + " occurred on Menu Item B")
          }
          }
        }
      )
    }
    items = products
  }

  override def setProducts(products_ : List[ProductDto]): Unit = {
    products.clear()
    products.appendAll(products_.map(p => ProductModel(p)))
  }
}
