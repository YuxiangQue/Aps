package com.acetec.aps.server.common

import ma.glasnost.orika.impl.DefaultMapperFactory
import ma.glasnost.orika.{Converter, MapperFactory}

object OrikaUtils {

  def defaultConverters: Seq[Converter[_, _]] = ScalaConverters.scalaConverters ++ DateTimeConverters.dateTimeConverters

  def createDefaultMapperFactory(): MapperFactory = {
    val factory = new DefaultMapperFactory.Builder().mapNulls(true).build()
    registerDefaultConverters(factory)
  }

  def registerDefaultConverters(factory: MapperFactory): MapperFactory = {
    defaultConverters.foreach(c => factory.getConverterFactory.registerConverter(c))
    factory
  }
}
