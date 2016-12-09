package com.acetec.aps.server.common


import java.util

import ma.glasnost.orika.metadata.{Type, TypeBuilder}
import ma.glasnost.orika.{Converter, MapperFacade}

import scala.collection.JavaConversions


object ScalaConverters {
  def scalaConverters: Seq[Converter[_, _]] = Seq[Converter[_, _]](
    //  Scala Seq[T] <-> java.util.List[T]
    new ListToSeq, new SeqToList,
    //  Option[T] <-> T
    new OptionToNullable, new NullableToOption
  )


  final class ListToSeq extends Converter[util.List[_], Seq[_]] {
    val getAType: Type[util.List[_]] = new TypeBuilder[util.List[_]]() {}.build()
    val getBType: Type[Seq[_]] = new TypeBuilder[Seq[_]]() {}.build()

    private var mapperFacade: MapperFacade = null

    override def setMapperFacade(mapper: MapperFacade): Unit = {
      this.mapperFacade = mapper
    }

    override def convert(source: util.List[_], destinationType: Type[_ <: Seq[_]]): Seq[_] = {
      val converted = mapperFacade.mapAsList(source, destinationType.getRawType)
      if (converted == null) {
        null
      } else {
        JavaConversions.asScalaBuffer(converted).toSeq
      }
    }

    override def canConvert(sourceType: Type[_], destinationType: Type[_]): Boolean = {
      getAType.isAssignableFrom(sourceType) && getBType.isAssignableFrom(destinationType)
    }
  }

  final class ListToJavaList extends Converter[List[_], util.List[_]] {
    val getAType: Type[List[_]] = new TypeBuilder[List[_]]() {}.build()
    val getBType: Type[util.List[_]] = new TypeBuilder[util.List[_]]() {}.build()

    private var mapperFacade: MapperFacade = null

    override def setMapperFacade(mapper: MapperFacade): Unit = {
      this.mapperFacade = mapper
    }

    override def canConvert(sourceType: Type[_], destinationType: Type[_]): Boolean = {
      getAType.isAssignableFrom(sourceType) && getBType.isAssignableFrom(destinationType)
    }

    override def convert(source: List[_], destinationType: Type[_ <: util.List[_]]): util.List[_] = {
      val iterable: java.lang.Iterable[_] =
        if (source == null) {
          null
        } else {
          JavaConversions.asJavaIterable(source)
        }
      mapperFacade.mapAsList(iterable, destinationType.getComponentType.getRawType)
    }
  }

  final class SeqToList extends Converter[Seq[_], util.List[_]] {
    val getAType: Type[Seq[_]] = new TypeBuilder[Seq[_]]() {}.build()
    val getBType: Type[util.List[_]] = new TypeBuilder[util.List[_]]() {}.build()

    private var mapperFacade: MapperFacade = null

    override def setMapperFacade(mapper: MapperFacade): Unit = {
      this.mapperFacade = mapper
    }

    override def canConvert(sourceType: Type[_], destinationType: Type[_]): Boolean = {
      getAType.isAssignableFrom(sourceType) && getBType.isAssignableFrom(destinationType)
    }

    override def convert(source: Seq[_], destinationType: Type[_ <: util.List[_]]): util.List[_] = {
      val iterable: java.lang.Iterable[_] =
        if (source == null) {
          null
        } else {
          JavaConversions.asJavaIterable(source)
        }
      mapperFacade.mapAsList(iterable, destinationType.getComponentType.getRawType)
    }
  }

  final class OptionToNullable extends Converter[Option[_], Any] {
    override val getAType: Type[Option[_]] = new TypeBuilder[Option[_]]() {}.build()
    override val getBType: Type[Any] = new TypeBuilder[Any]() {}.build()

    private var mapperFacade: MapperFacade = null

    override def setMapperFacade(mapper: MapperFacade): Unit = {
      this.mapperFacade = mapper
    }

    override def canConvert(sourceType: Type[_], destinationType: Type[_]): Boolean = {
      getAType.isAssignableFrom(sourceType) && destinationType.getActualTypeArguments.isEmpty
    }

    override def convert(source: Option[_], destinationType: Type[_ <: Any]): Any = {
      val value = source.getOrElse(null.asInstanceOf[Any])
      mapperFacade.map(value, destinationType.getRawType)
    }
  }

  final class NullableToOption extends Converter[Any, Option[_]] {
    override val getAType: Type[Any] = new TypeBuilder[Any]() {}.build()
    override val getBType: Type[Option[_]] = new TypeBuilder[Option[_]]() {}.build()

    private var mapperFacade: MapperFacade = null

    override def setMapperFacade(mapper: MapperFacade): Unit = {
      this.mapperFacade = mapper
    }

    override def canConvert(sourceType: Type[_], destinationType: Type[_]): Boolean = {
      getBType.isAssignableFrom(destinationType) && sourceType.getActualTypeArguments.isEmpty
    }

    override def convert(source: Any, destinationType: Type[_ <: Option[_]]): Option[_] = {
      Option(mapperFacade.map(source, destinationType.getRawType))
    }
  }

}
