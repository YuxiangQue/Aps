package com.acetec.aps.server.service

import com.acetec.aps.share.dto.TesterPlatformDto

trait TesterPlatformService {

  def createTesterPlatform(testerModelName: String, testerPlatform: TesterPlatformDto): TesterPlatformDto

  def findTesterPlatformByTesterPlatformName(testerPlatformName: String): TesterPlatformDto

  def findTesterPlatformsByTesterModelName(testerModelName: String): List[TesterPlatformDto]
}
