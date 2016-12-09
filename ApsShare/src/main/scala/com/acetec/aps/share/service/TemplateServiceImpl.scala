package com.acetec.aps.share.service

import java.io.InputStream

import com.acetec.aps.share.dto._
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._

class TemplateServiceImpl extends TemplateService {
  override def importProduct(is: InputStream): List[ProductDto] = {
    val workbook = new XSSFWorkbook(is)
    val sheet = workbook.getSheetAt(0)
    val rowIter = sheet.iterator()
    rowIter.next()

    val products = new ListBuffer[ProductDto]
    while (rowIter.hasNext) {
      val row = rowIter.next()

      val product = new ProductDto
      var colIndex = 0
      product.uniquePn = row.getCell(colIndex).getStringCellValue
      colIndex += 1
      product.customerPn = row.getCell(colIndex).getStringCellValue
      colIndex += 1
      product.process = row.getCell(colIndex).getStringCellValue
      colIndex += 1
      product.customer = row.getCell(colIndex).getStringCellValue
      colIndex += 1
      product.formFactor = row.getCell(colIndex).getStringCellValue
      colIndex += 1
      product.lotSize = row.getCell(colIndex).getNumericCellValue.toInt
      colIndex += 1
      product.dpw = row.getCell(colIndex).getNumericCellValue.toInt
      colIndex += 1
      product.yield1 = row.getCell(colIndex).getNumericCellValue
      colIndex += 1
      product.testerModel = row.getCell(colIndex).getStringCellValue
      colIndex += 1
      product.unitPriceUsd = row.getCell(colIndex).getNumericCellValue
      colIndex += 1

      product.parallelSite1 = row.getCell(colIndex).getNumericCellValue.toInt
      colIndex += 1
      product.touchDownEff1 = row.getCell(colIndex).getNumericCellValue
      colIndex += 1
      product.hr1Usd = row.getCell(colIndex).getNumericCellValue
      colIndex += 1
      product.indexTime1Sec = row.getCell(colIndex).getNumericCellValue
      colIndex += 1
      product.testTime1 = row.getCell(colIndex).getNumericCellValue
      colIndex += 1

      product.parallelSite2 = row.getCell(colIndex).getNumericCellValue.toInt
      colIndex += 1
      product.touchDownEff2 = row.getCell(colIndex).getNumericCellValue
      colIndex += 1
      product.hr2Usd = row.getCell(colIndex).getNumericCellValue
      colIndex += 1
      product.indexTime2Sec = row.getCell(colIndex).getNumericCellValue
      colIndex += 1
      product.testTime2 = row.getCell(colIndex).getNumericCellValue
      colIndex += 1

      products.append(product)
    }
    return products.toList
  }

  override def loadLot(is: InputStream): List[LotDto] = {
    val workbook = new XSSFWorkbook(is)
    val sheet = workbook.getSheetAt(0)
    val rowIter = sheet.iterator()
    rowIter.next()

    val lots = new ListBuffer[LotDto]
    while (rowIter.hasNext) {
      val row = rowIter.next()

      val lot = new LotDto
      var colIndex = 0
      lot.buCode1 = row.getCell(colIndex).getStringCellValue
      lot.buCode2 = lot.buCode1
      colIndex += 1
      lot.customer = row.getCell(colIndex).getStringCellValue
      colIndex += 1
      lot.customerPn = row.getCell(colIndex).getStringCellValue
      colIndex += 1
      lot.uniquePn = row.getCell(colIndex).getStringCellValue
      colIndex += 1
      lot.process = row.getCell(colIndex).getStringCellValue
      colIndex += 1
      lot.formFactor = row.getCell(colIndex).getStringCellValue
      colIndex += 1
      lot.lotNumber = row.getCell(colIndex).getStringCellValue
      colIndex += 1
      lot.lotSize = row.getCell(colIndex).getNumericCellValue.toInt

      colIndex += 1
      lot.passQty = row.getCell(colIndex).getNumericCellValue.toInt
      colIndex += 1
      lot.yield1 = row.getCell(colIndex).getNumericCellValue
      colIndex += 1
      lot.testerSn1 = row.getCell(colIndex).getStringCellValue
      lot.testerSn2 = lot.testerSn1
      colIndex += 1

      lot.flow = new FlowDto

      lot.flow.incomingDate = row.getCell(colIndex).getDateCellValue
      colIndex += 1
      lot.flow.moveInDate = row.getCell(colIndex).getDateCellValue
      colIndex += 1
      lot.flow.lotCreateDate = row.getCell(colIndex).getDateCellValue
      colIndex += 1
      lot.flow.testStartDate = row.getCell(colIndex).getDateCellValue
      colIndex += 1
      lot.flow.testEndDate = row.getCell(colIndex).getDateCellValue
      colIndex += 1
      lot.flow.moveOutDate = row.getCell(colIndex).getDateCellValue
      colIndex += 1
      lot.flow.outgoingDate = row.getCell(colIndex).getDateCellValue
      colIndex += 1

      lots.append(lot)
    }
    return lots.toList
  }

  override def loadModel(is: InputStream): List[TesterModelDto] = {
    val workbook = new XSSFWorkbook(is)
    val sheet = workbook.getSheetAt(0)
    val rowIter = sheet.iterator()

    val firstRow = rowIter.next()
    val firstRowCellIter = firstRow.cellIterator()
    firstRowCellIter.next() // skip A1

    val models = new ListBuffer[TesterModelDto]
    val modelToPlatforms = new mutable.HashMap[TesterModelDto, ListBuffer[TesterPlatformDto]]()
    while (firstRowCellIter.hasNext) {
      val testerModel = new TesterModelDto {
        name = firstRowCellIter.next().getStringCellValue
      }
      models.append(testerModel)
      modelToPlatforms.put(testerModel, new ListBuffer[TesterPlatformDto])
    }

    while (rowIter.hasNext) {
      val cellIter = rowIter.next().cellIterator()
      val cell = cellIter.next()
      if (cell.getCellType == Cell.CELL_TYPE_STRING) {
        val platformName = cell.getStringCellValue
        var colIndex = 1
        val testers = new ListBuffer[TesterDto]
        val platform = new TesterPlatformDto {
          name = platformName
        }
        while (cellIter.hasNext) {
          val cell = cellIter.next()
          if (cell.getCellType == Cell.CELL_TYPE_NUMERIC) {
            val model = models(colIndex - 1)
            val count = cell.getNumericCellValue.toInt
            (0 until count).foreach(testerIndex => {
              val testerSN = s"$platformName-${testerIndex - 1}"
              testers.append(new TesterDto {
                name = testerSN
              })
            })
            modelToPlatforms(model).append(platform)
          }
          colIndex += 1
        }
        platform.testers = testers
      }
    }

    models.foreach(m => {
      m.testerPlatforms = modelToPlatforms(m)
    })
    models.filter(m => m.name != null && m.name != "").toList
  }
}