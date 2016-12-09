package com.acetec.aps.client.view

import com.acetec.aps.client.presenter.CommandLogPresenter
import com.google.inject.Inject

import scalafx.Includes._
import scalafx.application.Platform
import scalafx.beans.property.{BooleanProperty, StringProperty}
import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, Label, TextField}
import scalafx.scene.layout.{BorderPane, HBox, VBox}

class CommandLogViewImpl @Inject()(presenter: CommandLogPresenter) extends CommandLogView {

  var message = new StringProperty(this, "message", "")
  var leaveVisible = new BooleanProperty(this, "leaveVisible", false)
  var joinVisible = new BooleanProperty(this, "joinProperty", false)

  var logs = new StringProperty(this, "logs", "")

  var content = new BorderPane {
    top = new HBox {
      children = List(new Button() {
        visible <== joinVisible
        text = "Join"
        onAction = (e: ActionEvent) => {
          presenter.join()
        }
      },
        new Button() {
          visible <== leaveVisible
          text = "Leave"
          onAction = (e: ActionEvent) => {
            presenter.leave()
          }
        })
    }
    center = new VBox {
      children = List(
        new Label {
          wrapText = true
          text <== logs
        }
      )
    }
    bottom = new HBox {
      children = List(
        new TextField {
          text <==> message
        },
        new Button {
          text = "Send"
          onAction = (e: ActionEvent) => {
            presenter.chat(message.value)
          }
        }
      )
    }
  }

  override def appendLog(log: String): Unit = {
    Platform.runLater(new Runnable {
      override def run() = {
        logs.value = logs.value + log + "\r\n"
      }
    })
  }

  override def showJoin() = {
    joinVisible.value = true
  }

  override def showLeave() = {
    leaveVisible.value = true
  }

  override def hideLeave(): Unit = {
    leaveVisible.value = false
  }

  override def hideJoin(): Unit = {
    joinVisible.value = false
  }

}



