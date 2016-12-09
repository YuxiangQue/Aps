package com.acetec.aps.server

import com.acetec.aps.server.service.{LotService, ProductService, TesterModelService}
import com.acetec.aps.share.service.TemplateServiceImpl
import com.codahale.metrics.{JmxReporter, MetricRegistry}
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner
import com.corundumstudio.socketio.protocol.JacksonJsonSupport
import com.corundumstudio.socketio.{AuthorizationListener, Configuration, HandshakeData, SocketIOServer}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.{CommandLineRunner, SpringApplication}
import org.springframework.context.annotation.Bean

object Application {
  val secret = "Who am I?"

  @throws[Exception]
  def main(args: Array[String]) {
    SpringApplication.run(classOf[Application], args: _*)
  }
}

@SpringBootApplication
class Application {
  private val logger = LoggerFactory.getLogger(classOf[Application])

  @Autowired
  private var metricRegistry: MetricRegistry = _

  @Autowired
  private var productService: ProductService = _

  @Autowired
  private var testerModelService: TesterModelService = _

  @Autowired
  private var lotService: LotService = _

  @Bean
  def templateService() = new TemplateServiceImpl

  @Bean
  def socketIOServer: SocketIOServer = {
    val configuration = new Configuration
    configuration.setHostname("localhost")
    configuration.setPort(7964)
    // 支持scala对象的
    configuration.setJsonSupport(new JacksonJsonSupport(new DefaultScalaModule))
    configuration.setAuthorizationListener(new AuthorizationListener() {
      def isAuthorized(handshakeData: HandshakeData) = true
    })
    new SocketIOServer(configuration)
  }

  @Bean
  def setupProducts: CommandLineRunner = {
    return new CommandLineRunner {
      override def run(strings: String*) = {
        val cl = getClass.getClassLoader
        val service = templateService()
        val prodcuts = service.importProduct(cl.getResourceAsStream("com/acetec/aps/template/Product.xlsx"))
        productService.deleteAllProducts()
        prodcuts.foreach(p => {
          productService.saveProduct(p)
        })

        var testerModels = service.loadModel(cl.getResourceAsStream("com/acetec/aps/template/ParamModel.xlsx"))
        testerModels.foreach(tm => {
          testerModelService.saveModel(tm)
        })

        var lots = service.loadLot(cl.getResourceAsStream("com/acetec/aps/template/UrgentLot.xlsx"))
        lots.foreach(l => {
          lotService.saveLot(l)
        })
      }
    }
  }

  @Bean def socketIOServerRunner: CommandLineRunner = {
    return new CommandLineRunner {
      override def run(strings: String*) = {
        socketIOServer.start()
      }
    }
  }

  @Bean def springAnnotationScanner(socketIOServer: SocketIOServer) = new SpringAnnotationScanner(socketIOServer)

  @throws[Exception]
  def afterPropertiesSet() {
    JmxReporter.forRegistry(metricRegistry).build.start()
  }
}

