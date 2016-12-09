package com.acetec.aps.server.service

import com.acetec.aps.share.dto.TesterDto

trait TesterService {
  def createTester(testerPlatformName: String, tester: TesterDto): TesterDto

  def findTesterByTesterName(testerName: String): TesterDto

  def findTestersByTesterPlatformName(testerPlatformName: String): List[TesterDto]
}
