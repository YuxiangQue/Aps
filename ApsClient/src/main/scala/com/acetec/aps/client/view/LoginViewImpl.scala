package com.acetec.aps.client.view

import com.acetec.aps.client.presenter.LoginPresenter
import com.google.inject.Inject

import scalafx.Includes._
import scalafx.application.Platform
import scalafx.beans.property.{BooleanProperty, StringProperty}
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.control._
import scalafx.scene.layout.GridPane
import scalafx.stage.Window

class LoginViewImpl @Inject()(presenter: LoginPresenter) extends LoginView {

  var progressIndicatorVisible = new BooleanProperty(this, "progressIndicatorVisible", false)
  var errorVisible = new BooleanProperty(this, "errorVisible", false)
  var errorText = new StringProperty(this, "errorText", "")
  var username = new StringProperty(this, "username", "")
  var password = new StringProperty(this, "password", "")

  var progressIndicator = new ProgressIndicator {
  }

  var errorLabel = new Label {
    text <== errorText
  }

  case class Result(username: String, password: String)

  val loginButtonType = new ButtonType("Login")

  var dialog = new Dialog[Result]() {
    title = "Login"
    headerText = "Please login first!"
    resultConverter = dialogButton => {
      if (dialogButton == loginButtonType) {
        Result(username.value, password.value)
      } else {
        null
      }
    }
  }

  dialog.dialogPane().buttonTypes = Seq(loginButtonType, ButtonType.Cancel)
  val pane = new GridPane() {
    hgap = 10
    vgap = 10
    padding = Insets(20, 100, 10, 10)
    add(new Label("Username:"), 0, 0)
    add(new TextField {
      inner =>
      promptText = "Enter username"
      text <==> username
      Platform.runLater(new Runnable {
        override def run() = {
          inner.requestFocus()
        }
      })
    }, 1, 0)
    add(new Label("Password"), 0, 1)
    add(new PasswordField {
      promptText = "Enter password"
      text <==> password
    }, 1, 1)

  }
  dialog.dialogPane().content = pane

  var loginButton = dialog.dialogPane().lookupButton(loginButtonType).asInstanceOf[javafx.scene.control.Button]
  var cancelButton = dialog.dialogPane().lookupButton(ButtonType.Cancel).asInstanceOf[javafx.scene.control.Button]

  // 拦截LoginButton导致的Dialog关闭
  loginButton.onAction = { ae: ActionEvent =>

  }

  override def setLoggedInError() = {
    Platform.runLater(new Runnable {
      override def run() = {
        errorVisible.value = true
        errorText.value = "User has already logged in!"
      }
    })
  }

  override def showProgress(): Unit = {
    // Todo:
    //    pane.children.remove(errorLabel)
    //    pane.add(progressIndicator, 0, 3, 2, 1)
    progressIndicatorVisible.value = true
  }

  override def hideProgress(): Unit = {
    // Todo:
    //    pane.children.remove(progressIndicator)
    progressIndicatorVisible.value = false
  }

  override def setUsernameError(): Unit = {
    // Todo:
    //    pane.add(errorLabel, 0, 2, 2, 1)
    errorText.value = "Username error!"
  }

  override def setPasswordError(): Unit = {
    pane.add(errorLabel, 0, 2, 2, 1)
    errorText.value = "Username error!"
  }

  private var canExit = false

  override def showAndWait(owner: Window): Unit = {
    dialog.initOwner(owner)
    val result = dialog.showAndWait()
    result match {
      case Some(Result(u, p)) => presenter.validateCredentials(u, p)
      case None =>
    }
  }

  override def hide(): Unit = {
    printf("hide\n")
    canExit = true
    dialog.close()
  }
}
