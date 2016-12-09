package com.acetec.aps.client.view

import java.io.File

import com.acetec.aps.client._
import com.acetec.aps.client.model.{AppModel, ProductModel}
import com.acetec.aps.share.service.TemplateServiceImpl
import org.dockfx.{DockNode, DockPane, DockPos}

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.event.{ActionEvent, Event}
import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.control.MenuItem._
import scalafx.scene.control.ScrollPane.ScrollBarPolicy
import scalafx.scene.control._
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.input.KeyCombination
import scalafx.scene.layout.{BorderPane, HBox, StackPane, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Circle, Rectangle}
import scalafx.scene.web.{HTMLEditor, WebView}
import scalafx.scene.{Node, Scene}
import scalafx.stage.{FileChooser, Popup, Stage}


object App extends JFXApp {

  var model = new AppModel()

  stage = new PrimaryStage {
    scene = new Scene(800, 600) {
      stylesheets = List(getClass.getResource("jbootx/bootstrap3.css").toExternalForm)
      root = new BorderPane {
        top = new VBox {
          children = List(
            createMenus(),
            createToolBar()
          )
          center = createTabs()
        }
      }
    }
    icons += new Image(getClass.getResourceAsStream("icon.png"))
    title = "APS"
  }

  private def createMenus() = new MenuBar {
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
      },
      new Menu("File") {
        items = List(
          new MenuItem("New...") {
            graphic = new ImageView(new Image(this, "images/paper.png"))
            accelerator = KeyCombination.keyCombination("Ctrl +N")
            onAction = {
              e: ActionEvent => println(e.eventType + " occurred on MenuItem New")
            }
          },
          new MenuItem("Save")
        )
      },
      new Menu("Edit") {
        items = List(
          new MenuItem("Cut"),
          new MenuItem("Copy"),
          new MenuItem("Paste")
        )
      }
    )
  }


  private def createToolBar(): ToolBar = {
    val alignToggleGroup = new ToggleGroup()
    val toolBar = new ToolBar {
      content = List(
        new Button {
          id = "newButton"
          graphic = new ImageView(new Image(this, "images/paper.png"))
          tooltip = Tooltip("New Document... Ctrl+N")
          onAction = handle {
            println("New toolbar button clicked")
          }
        },
        new Button {
          id = "editButton"
          graphic = new Circle {
            fill = Color.Green
            radius = 8
          }
        },
        new Button {
          id = "deleteButton"
          graphic = new Circle {
            fill = Color.Blue
            radius = 8
          }
        },
        new Separator {
          orientation = Orientation.Vertical
        },
        new ToggleButton {
          id = "boldButton"
          graphic = new Circle {
            fill = Color.Maroon
            radius = 8
          }
          onAction = {
            e: ActionEvent =>
              val tb = e.getTarget.asInstanceOf[javafx.scene.control.ToggleButton]
              print(e.eventType + " occurred on ToggleButton " + tb.id)
              print(", and selectedProperty is: ")
              println(tb.selectedProperty.value)
          }
        },
        new ToggleButton {
          id = "italicButton"
          graphic = new Circle {
            fill = Color.Yellow
            radius = 8
          }
          onAction = {
            e: ActionEvent =>
              val tb = e.getTarget.asInstanceOf[javafx.scene.control.ToggleButton]
              print(e.eventType + " occurred on ToggleButton " + tb.id)
              print(", and selectedProperty is: ")
              println(tb.selectedProperty.value)
          }
        },
        new Separator {
          orientation = Orientation.Vertical
        },
        new ToggleButton {
          id = "leftAlignButton"
          toggleGroup = alignToggleGroup
          graphic = new Circle {
            fill = Color.Purple
            radius = 8
          }
        },
        new ToggleButton {
          toggleGroup = alignToggleGroup
          id = "centerAlignButton"
          graphic = new Circle {
            fill = Color.Orange
            radius = 8
          }
        },
        new ToggleButton {
          toggleGroup = alignToggleGroup
          id = "rightAlignButton"
          graphic = new Circle {
            fill = Color.Cyan
            radius = 8
          }
        }
      )
    }

    alignToggleGroup.selectToggle(alignToggleGroup.toggles(0))
    alignToggleGroup.selectedToggle.onChange {
      val tb = alignToggleGroup.selectedToggle.get.asInstanceOf[ToggleButton]
      println(tb.id() + " selected")
    }

    toolBar
  }


  private def createTabs(): TabPane = {
    new TabPane {
      tabs = List(
        new Tab {
          text = "Accordion/TitledPane"
          content = createAccordionTitledDemoNode()
          closable = false
        },
        new Tab {
          text = "SplitPane/TreeView/ListView"
          content = createSplitTreeListDemoNode()
          closable = false
        },
        new Tab {
          text = "ScrollPane/Miscellaneous"
          content = createScrollMiscDemoNode()
          closable = false
        },
        new Tab {
          text = "HTMLEditor"
          content = createHtmlEditorDemoNode()
          closable = false
        },
        new Tab {
          // Name a reference to itself
          inner =>
          val webView = new WebView()
          text = "WebView"
          content = webView
          closable = false
          onSelectionChanged = (e: Event) => {
            val randomWebSite = model.randomWebSite()
            if (inner.selected()) {
              webView.engine.load(randomWebSite)
              println("WebView tab is selected, loading: " + randomWebSite)
            }
            println("")
          }
        }
      )
    }
  }

  //  private def createScheduledLotGanttView(): TabPane = {
  //    new TabPane {
  //      tabs = model.getTesterModels.map(m => new Tab {
  //        text = m.name
  //        closable = false
  //      })
  //    }
  //  }
  //
  //  def createUnscheduledRegularLotListView(): TabPane = {

  //  }
  //
  //  def createUnscheduledUrgentLotListView(): TabPane = {
  //    val tabPane = new TabPane {
  //      tabs = model.getTesterModels.map(m => new Tab {
  //        text = m.name
  //        closable = false
  //        content = {
  //          // Create columns
  //          val lotNumberColumn = new TableColumn[LotModel, String] {
  //            text = "LotNumber"
  //            cellValueFactory = {
  //              _.value.lotNumber
  //            }
  //          }
  //          val uniquePNColumn = new TableColumn[LotModel, String] {
  //            text = "UniquePN"
  //            cellValueFactory = {
  //              _.value.uniquePN
  //            }
  //          }
  //          val customerColumn = new TableColumn[LotModel, String] {
  //            text = "Customer"
  //            cellValueFactory = {
  //              _.value.customer
  //            }
  //          }
  //          val customerPnColumn = new TableColumn[LotModel, String] {
  //            text = "CustomerPN"
  //            cellValueFactory = {
  //              _.value.customerPN
  //            }
  //          }
  //          val processColumn = new TableColumn[LotModel, String] {
  //            text = "Process"
  //            cellValueFactory = {
  //              _.value.process
  //            }
  //          }
  //          val testerModelColumn = new TableColumn[LotModel, String] {
  //            text = "TesterModel"
  //            cellValueFactory = {
  //              _.value.testerModel
  //            }
  //          }
  //          val hr2UsdColumn = new TableColumn[LotModel, String] {
  //            text = "HR2USD"
  //            cellValueFactory = {
  //              _.value.hr2Usd
  //            }
  //          }
  //          val lotSizeColumn = new TableColumn[LotModel, Int] {
  //            text = "LotSize"
  //            cellValueFactory = {
  //              _.value.lotSize
  //            }
  //          }
  //          // Create table
  //          val table = new TableView[LotModel](model.getUnscheduledUrgentLots(m.name)) {
  //            columns += (lotNumberColumn,
  //              uniquePNColumn,
  //              customerColumn,
  //              customerPnColumn,
  //              processColumn,
  //              testerModelColumn,
  //              hr2UsdColumn,
  //              lotSizeColumn
  //            )
  //          }
  //          table.contextMenu = new ContextMenu {
  //            items += (
  //              new MenuItem("MenuItemA") {
  //                onAction = { e: ActionEvent => println(e.eventType + " occurred on Menu Item A") }
  //              },
  //              new MenuItem("MenuItemB") {
  //                onAction = { e: ActionEvent => println(e.eventType + " occurred on Menu Item B") }
  //              })
  //          }
  //          // Listen to row selection, and print values of the selected row
  //          table.selectionModel().selectedItem.onChange(
  //            (_, _, newValue) => {
  //              println(newValue + " chosen in TableView")
  //            }
  //          )
  //          table
  //        }
  //      })
  //    }
  //    tabPane
  //  }


  private def createProductListView(): Node = {
    // Create columns
    val nameCol = new TableColumn[ProductModel, String] {
      text = "Name"
      cellValueFactory = {
        _.value.uniquePn
      }
    }
    val customerCol = new TableColumn[ProductModel, String] {
      text = "Customer"
      cellValueFactory = {
        _.value.customer
      }
    }
    val customerPNCol = new TableColumn[ProductModel, String] {
      text = "CustomerPN"
      cellValueFactory = {
        _.value.customerPn
      }
    }

    // Create table
    val table = new TableView[ProductModel]() {
      innerTable =>
      columns += (nameCol, customerCol, customerPNCol)
      contextMenu = new ContextMenu {
        items += (
          new MenuItem("来料") {
            onAction = { e: ActionEvent => {
              val item = innerTable.selectionModel().selectedItem.value
              printf(s"来料 ${item.uniquePn}")
              val stage = new Stage {
                inner =>
                title = "来料"
                scene = new Scene {
                  root = new BorderPane {
                    center = new VBox {
                      children = List(
                        new TextField {
                          text = "0"
                        },
                        new HBox {
                          alignment = Pos.BaselineRight
                          children = List(new Button {
                            text = "确定"
                            onAction = (e: ActionEvent) => {
                              inner.close()
                            }
                          }, new Button {
                            text = "取消"
                            onAction = (e: ActionEvent) => {
                              inner.close()
                            }
                          })
                        }
                      )
                    }
                  }
                }
              }
              stage.showAndWait()
            }
            }
          },
          new MenuItem("编辑") {
            onAction = { e: ActionEvent => {
              println(e.eventType + " occurred on Menu Item B")
            }
            }
          })
      }
    }
    // Listen to row selection, and print values of the selected row
    table.selectionModel().selectedItem.onChange(
      (_, _, newValue) => println(newValue + " chosen in TableView")
    )
    table
  }

  private def createAccordionTitledDemoNode(): Node = new Accordion {
    panes = List(
      new TitledPane() {
        text = "TitledPane A"
        content = new TextArea {
          text = "TitledPane A content"
        }
      },
      new TitledPane {
        text = "TitledPane B"
        content = new TextArea {
          text = "TitledPane B content"
        }
      },
      new TitledPane {
        text = "TitledPane C"
        content = new TextArea {
          text = "TitledPane C' content"
        }
      }
    )

    expandedPane = panes.head
  }

  private def createSplitTreeListDemoNode(): Node = {
    val treeView = new TreeView[String] {
      minWidth = 150
      showRoot = false
      editable = false
      root = new TreeItem[String] {
        value = "Root"
        children = List(
          new TreeItem("Animal") {
            children = List(
              new TreeItem("Lion"),
              new TreeItem("Tiger"),
              new TreeItem("Bear")
            )
          },
          new TreeItem("Mineral") {
            children = List(
              new TreeItem("Copper"),
              new TreeItem("Diamond"),
              new TreeItem("Quartz")
            )
          },
          new TreeItem("Vegetable") {
            children = List(
              new TreeItem("Arugula"),
              new TreeItem("Broccoli"),
              new TreeItem("Cabbage")
            )
          }
        )
      }
    }

    val listView = new ListView[String] {
      items = model.listViewItems
    }

    treeView.selectionModel().selectionMode = SelectionMode.Single
    treeView.selectionModel().selectedItem.onChange(
      (_, _, newTreeItem) => {
        if (newTreeItem != null && newTreeItem.isLeaf) {
          model.listViewItems.clear()
          for (i <- 1 to 10000) {
            model.listViewItems += newTreeItem.getValue + " " + i
          }
        }
      }
    )

    new SplitPane {
      items ++= List(
        treeView,
        listView
      )
    }
  }

  private def createScrollMiscDemoNode(): Node = {
    val radioToggleGroup = new ToggleGroup()
    val variousControls = new VBox {
      padding = Insets(10)
      spacing = 20
      children = List(
        new Button("Button") {
          onAction = {
            e: ActionEvent => println(e.eventType + " occurred on Button")
          }
        },
        new CheckBox("CheckBox") {
          inner =>
          onAction = {
            e: ActionEvent =>
              println(e.eventType + " occurred on CheckBox, and `selected` property is: " + inner.selected())
          }
        },
        new HBox {
          spacing = 10
          children = List(
            new RadioButton("RadioButton1") {
              toggleGroup = radioToggleGroup
            },
            new RadioButton("RadioButton2") {
              toggleGroup = radioToggleGroup
            }
          )
        },
        new Hyperlink("Hyperlink") {
          onAction = {
            e: ActionEvent => println(e.eventType + " occurred on Hyperlink")
          }
        },
        new ChoiceBox(model.choiceBoxItems) {
          selectionModel().selectFirst()
          selectionModel().selectedItem.onChange(
            (_, _, newValue) => println(newValue + " chosen in ChoiceBox")
          )
        },
        new MenuButton("MenuButton") {
          items = List(
            new MenuItem("MenuItem A") {
              onAction = {
                ae: ActionEvent => println(ae.eventType + " occurred on Menu Item A")
              }
            },
            new MenuItem("MenuItem B")
          )
        },
        new SplitMenuButton {
          text = "SplitMenuButton"
          onAction = {
            ae: ActionEvent => println(ae.eventType + " occurred on SplitMenuButton")
          }
          items = List(
            new MenuItem("MenuItem A") {
              onAction = {
                ae: ActionEvent => println(ae.eventType + " occurred on Menu Item A")
              }
            },
            new MenuItem("MenuItem B")
          )
        },
        new TextField {
          promptText = "Enter user name"
          prefColumnCount = 16
          text.onChange {
            println("TextField text is: " + text())
          }
        },
        new PasswordField {
          promptText = "Enter password"
          prefColumnCount = 16
          text.onChange {
            println("PasswordField text is: " + text())
          }
        },
        new HBox {
          spacing = 10
          children = List(
            new Label {
              text = "TextArea"
            },
            new TextArea {
              prefColumnCount = 12
              prefRowCount = 4
              text.onChange {
                println("TextArea text is: " + text())
              }
            }
          )
        },
        new ProgressIndicator {
          prefWidth = 200
          progress <== model.rpm / model.maxRpm
        },
        new Slider {
          prefWidth = 200
          min = -1
          max = model.maxRpm
          value <==> model.rpm
        },
        new ProgressBar {
          prefWidth = 200
          progress <== model.kph / model.maxKph
        },
        new ScrollBar {
          prefWidth = 200
          min = -1
          max = model.maxKph
          value <==> model.kph
        }
      )
    }

    radioToggleGroup.selectToggle(radioToggleGroup.toggles(0))
    radioToggleGroup.selectedToggle.onChange {
      val rb = radioToggleGroup.selectedToggle.get.asInstanceOf[javafx.scene.control.ToggleButton]
      if (rb != null) println(rb.id() + " selected")
    }

    val sampleContextMenu = new ContextMenu {
      items += (
        new MenuItem("MenuItemA") {
          onAction = { e: ActionEvent => println(e.eventType + " occurred on Menu Item A") }
        },
        new MenuItem("MenuItemB") {
          onAction = { e: ActionEvent => println(e.eventType + " occurred on Menu Item B") }
        })
    }

    new ScrollPane {
      content = variousControls
      hbarPolicy = ScrollBarPolicy.Always
      vbarPolicy = ScrollBarPolicy.AsNeeded
      contextMenu = sampleContextMenu
    }
  }

  private def createHtmlEditorDemoNode(): Node = {

    val htmlEditor = new HTMLEditor {
      htmlText = "<p>Replace this text</p>"
    }

    val viewHTMLButton = new Button("View HTML") {
      onAction = {
        e: ActionEvent => {
          val alertPopup = createAlertPopup(htmlEditor.htmlText)
          alertPopup.show(stage,
            (stage.width() - alertPopup.width()) / 2.0 + stage.x(),
            (stage.height() - alertPopup.height()) / 2.0 + stage.y())
        }
      }
      alignmentInParent = Pos.Center
      margin = Insets(10, 0, 10, 0)
    }

    new BorderPane {
      center = htmlEditor
      bottom = viewHTMLButton
    }
  }

  private def createAlertPopup(popupText: String) = new Popup {
    inner =>
    content.add(new StackPane {
      children = List(
        new Rectangle {
          width = 300
          height = 200
          arcWidth = 20
          arcHeight = 20
          fill = Color.LightBlue
          stroke = Color.Gray
          strokeWidth = 2
        },
        new BorderPane {
          center = new Label {
            text = popupText
            wrapText = true
            maxWidth = 280
            maxHeight = 140
          }
          bottom = new Button("OK") {
            onAction = { e: ActionEvent => inner.hide() }
            alignmentInParent = Pos.Center
            margin = Insets(10, 0, 10, 0)
          }
        }
      )
    }.delegate
    )
  }

}
