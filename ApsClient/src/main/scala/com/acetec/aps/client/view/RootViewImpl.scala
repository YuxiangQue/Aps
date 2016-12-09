package com.acetec.aps.client.view

import java.io.File

import com.acetec.aps.client.presenter.RootPresenter
import com.acetec.aps.client.view.App.stage
import com.google.inject.Inject
import org.dockfx.{DockNode, DockPane, DockPos}

import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.control._
import scalafx.scene.layout.{BorderPane, VBox}
import scalafx.stage.FileChooser


class RootViewImpl @Inject()(presenter: RootPresenter,
                             loginView: LoginView,
                             productListView: ProductListView,
                             lotListView: LotListView,
                             lotGanttView: LotGanttView,
                             testerModelTreeView: TesterModelTreeView,
                             commandLogView: CommandLogView
                            ) extends RootView {
  var content = new BorderPane {
    top = new VBox {
      children = List(
        new MenuBar {
          menus = List(
            new Menu("导入") {
              items = List(
                new MenuItem("产品") {
                  onAction = {
                    e: ActionEvent => {
                      val fc = new FileChooser {
                        title = "产品"
                        initialDirectory = new File(System.getProperty("user.home"))
                      }
                      val file = fc.showOpenDialog(stage)
                      //                ies.loadModel(new FileInputStream(file))
                    }
                  }
                },
                new MenuItem("机台"),
                new MenuItem("插单Lot"),
                new MenuItem("Rnd")
              )
            },
            new Menu("导出") {
              items = List(
                new MenuItem("产品"),
                new MenuItem("排产"))
            },
            new Menu("视图") {
              items = List(
                new MenuItem("产品一览"),
                new MenuItem("机台一览"),
                new MenuItem("调度图"),
                new MenuItem("普通Lot池"),
                new MenuItem("插单Lot池"))
            }
          )
        },
        new ToolBar {
          content = List(new Button {
            id = "newButton"
            text = "保存"
            tooltip = Tooltip("New Document... Ctrl+N")
            onAction = handle {
              println("New toolbar button clicked")
            }
          })
        }
      )
    }
    bottom = dockPane()
  }

  def dockPane(): DockPane = {
    val dockPane = new DockPane
    var dockNode = new DockNode(productListView.content, "产品一览")
    dockNode.dock(dockPane, DockPos.LEFT)

    dockNode = new DockNode(lotListView.content, "Lot列表")
    dockNode.dock(dockPane, DockPos.LEFT)

    dockNode = new DockNode(commandLogView.content, "操作日志")
    dockNode.dock(dockPane, DockPos.LEFT)

    dockNode = new DockNode(lotGanttView.content, "Lot甘特图")
    dockNode.dock(dockPane, DockPos.LEFT)

    DockPane.initializeDefaultUserAgentStylesheet()
    dockPane
  }

}
