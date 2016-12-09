package client.service

import com.acetec.aps.share.service.TemplateServiceImpl
import org.scalatest.{FlatSpec, Matchers}

class ImportExportServiceSpec extends FlatSpec with Matchers {

  "TesterModel" should "" in {
    val ie = new TemplateServiceImpl
    val modelIs = classOf[ImportExportServiceSpec].getResourceAsStream("ParamModel.xlsx")
    val models = ie.loadModel(modelIs)
  }

  "UrgentLot" should "" in {
    val ie = new TemplateServiceImpl
    val urgentLotIs = classOf[ImportExportServiceSpec].getResourceAsStream("UrgentLot.xlsx")
    val lots = ie.loadLot(urgentLotIs)
    lots.size should be(200)
    val firstLot = lots.head
    firstLot.uniquePn should be("CP-A-750EX-8")
    firstLot.process should be("CP")
    firstLot.formFactor should be(8)
    firstLot.lotNumber should be("A9DYLNBOSO")
    firstLot.lotSize should be(25)
    firstLot.passQty should be(0)
    firstLot.yield1 should be(0)
    firstLot.testerSn1 should be("J750EX8-000")
    firstLot.testerSn2 should be("J750EX8-000")
    //    firstLot.flow.incomingDate should be "CP-A-750EX-8"
    //    firstLot.flow.moveInDate should be "CP-A-750EX-8"
    //    firstLot.flow.lotCreateDate should be "CP-A-750EX-8"
    //    firstLot.flow.testStartDate should be "CP-A-750EX-8"
    //    firstLot.flow.testEndDate should be "CP-A-750EX-8"
    //    firstLot.flow.moveOutDate should be "CP-A-750EX-8"
    //    firstLot.flow.outgoingDate should be "CP-A-750EX-8"
  }

  "Product" should "" in {
    val ie = new TemplateServiceImpl
    val productIs = classOf[ImportExportServiceSpec].getResourceAsStream("Product.xlsx")
    val products = ie.importProduct(productIs)
  }
}
