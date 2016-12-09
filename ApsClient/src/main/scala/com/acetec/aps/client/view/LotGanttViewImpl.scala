package com.acetec.aps.client.view

import org.joda.time.{DateTime, Days}

import scala.collection.mutable.ListBuffer
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.control.ScrollPane
import scalafx.scene.paint.Color

class LotGanttViewImpl extends LotGanttView {

  class Gantt(val canvas: Canvas) {

    canvas.onMouseClicked = (e) => {
      columns.foreach(row => {
        row.picked = false
      })
      columns.foreach(row => {
        if (row.contains(e.getX, e.getY)) {
          row.picked = true
        }
      })
      render()
    }

    val gc = canvas.graphicsContext2D

    var multiSelect = false

    val rowHeaderWidth = 32
    val rowHeaderHeight = 128
    val columnHeaderHeight = 32
    val columnHeaderWidth = 64

    val rows = new ListBuffer[Row]
    val columns = new ListBuffer[Column]

    def addRow(row: Row) = rows.append(row)

    def addCol(col: Column) = columns.append(col)

    def ganttWidth(): Int = {
      columns.size * columnHeaderWidth + rowHeaderWidth
    }

    def ganttHeight(): Int = {
      rows.size * rowHeaderHeight + columnHeaderHeight
    }

    def render(): Unit = {
      gc.fill = Color.White
      gc.fillRect(0, 0, canvas.width.value, canvas.height.value)
      gc.rect(0, 0, rowHeaderWidth, columnHeaderHeight)
      rows.foreach(row => row.render(gc))
      columns.foreach(col => col.render(gc))
    }
  }

  class Column(val gantt: Gantt, index: Int, text: String) {
    def contains(getX: Double, getY: Double) = {
      if (getX < x || getX > x + width)
        false
      else if (getY < y || getY > y + height)
        false
      else
        true
    }

    var picked = false

    def insects = 1

    def x = gantt.rowHeaderWidth + index * gantt.columnHeaderWidth

    def y = 0

    def width = gantt.columnHeaderWidth

    def height = gantt.rows.size * gantt.rowHeaderHeight + gantt.columnHeaderHeight

    def headerHeight = gantt.columnHeaderHeight

    def render(gc: GraphicsContext): Unit = {
      if (picked) {
        gc.fill = Color.rgb(238, 147, 17)
        gc.fillRect(
          x + insects,
          y + insects,
          width - insects * 2,
          headerHeight - insects * 2
        )
        gc.fill = Color.White
        gc.fillText(text, x + insects, 0 + headerHeight / 2)

        gc.stroke = Color.rgb(238, 147, 17)
        gc.strokeLine(x + insects,
          headerHeight,
          x + insects,
          height - headerHeight
        )
        gc.strokeLine(x + width - insects * 2,
          headerHeight,
          x + width - insects * 2,
          height - headerHeight
        )
      } else {
        gc.fill = Color.rgb(0x0, 0x7b, 0xd7)
        gc.fillRect(
          x + insects,
          y + insects,
          width - insects * 2,
          headerHeight - insects * 2
        )
        gc.fill = Color.White
        gc.fillText(text, x + insects, 0 + headerHeight / 2)
      }
    }
  }

  class Row(val gantt: Gantt, index: Int) {
    def insects = 1

    def x = 0

    def y = gantt.columnHeaderHeight + index * gantt.rowHeaderHeight

    def width = gantt.columns.size * gantt.columnHeaderWidth + gantt.rowHeaderWidth

    def height = gantt.rowHeaderHeight

    def heightWidth = gantt.rowHeaderWidth

    def render(gc: GraphicsContext): Unit = {
      gc.fill = Color.rgb(0x0, 0x7b, 0xd7)
      gc.fillRect(
        0 + insects,
        y + insects,
        heightWidth - insects * 2,
        height - insects * 2
      )
      gc.fill = Color.White

      gc.stroke = Color.rgb(238, 147, 17)
      gc.strokeLine(heightWidth,
        y + insects,
        width - heightWidth,
        y + insects
      )
    }
  }

  var columns = (0 to 16).map(i => s"3360P-$i")
  val from = DateTime.now().withDayOfMonth(1)
  val to = DateTime.now().withDayOfMonth(31)
  val rows = (0 until Days.daysBetween(from, to).getDays)


  val canvas = new Canvas(640, 480) {
  }
  val gantt = new Gantt(canvas)
  rows.indices.foreach(index => gantt.addRow(new Row(gantt, index)))
  columns.indices.foreach(index => gantt.addCol(new Column(gantt, index, columns(index))))
  gantt.render()

  override def content = new ScrollPane {
    content = canvas
  }
}
