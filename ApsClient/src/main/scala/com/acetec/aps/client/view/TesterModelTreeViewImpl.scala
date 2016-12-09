package com.acetec.aps.client.view


import com.acetec.aps.client.presenter.TesterModelPresenter
import com.acetec.aps.share.dto.{TesterDto, TesterModelDto, TesterPlatformDto}
import com.google.inject.Inject

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{TreeItem, TreeView}

class TesterModelTreeViewImpl @Inject()(presenter: TesterModelPresenter) extends TesterModelTreeView {

  val vm = ObservableBuffer[TreeItem[String]]()

  override def content = new TreeView[String] {
    showRoot = true
    minHeight = 200
    minWidth = 200
    root = new TreeItem[String]("Root Node") {
      expanded = true
      children = vm
    }
  }

  def toTreeItem(testerModel: TesterModelDto): TreeItem[String] =
    new TreeItem[String] {
      value = testerModel.name
      children = ObservableBuffer(testerModel.testerPlatforms.map(toTreeItem))
    }

  def toTreeItem(testerPlatform: TesterPlatformDto): TreeItem[String] =
    new TreeItem[String] {
      value = testerPlatform.name
      children = ObservableBuffer(testerPlatform.testers.map(toTreeItem))
    }

  def toTreeItem(tester: TesterDto): TreeItem[String] =
    new TreeItem[String] {
      value = tester.name
    }

  override def setTesterModels(testerModels: List[TesterModelDto]): Unit = {
    vm.clear()
    val treeItems = testerModels.map(m => toTreeItem(m)).asJava
    vm.addAll(treeItems)
  }
}
