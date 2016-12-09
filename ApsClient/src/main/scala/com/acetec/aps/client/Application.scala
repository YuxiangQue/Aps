package com.acetec.aps.client

import com.acetec.aps.client.service.LoginService
import com.acetec.aps.client.view.{LoginView, RootViewImpl}
import com.google.inject.Guice

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.image.Image

object Application extends JFXApp {

  import net.codingwell.scalaguice.InjectorExtensions._

  val injector = Guice.createInjector(new ApplicationModule)

  var rootView = injector.instance[RootViewImpl]

  var loginView = injector.instance[LoginView]

  var loginService = injector.instance[LoginService]

  stage = new PrimaryStage {
    scene = new Scene() {
      stylesheets = List(getClass.getResource("view/jbootx/bootstrap3.css").toExternalForm)
      content = rootView.content
    }
    icons += new Image(getClass.getResourceAsStream("view/icon.png"))
    title = "APS"
  }

  stage.onShown = { e =>
    // loginView.showAndWait(stage)
  }
}
