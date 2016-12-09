package com.acetec.aps.server.common

import java.sql
import java.sql.Timestamp
import java.util.Date

import ma.glasnost.orika.converter.builtin.PassThroughConverter
import ma.glasnost.orika.metadata.Type
import ma.glasnost.orika.{Converter, CustomConverter}
import org.joda.time.{DateTime, DateTimeZone, LocalDate}

object DateTimeConverters {
  def dateTimeConverters: Seq[Converter[_, _]] = Seq[Converter[_, _]](
    //  JodaTime literal definitions
    new PassThroughConverter(classOf[DateTime], classOf[LocalDate]),
    //  DateTime <-> Unix Epoch
    new DateTimeToLong, new LongToDateTime,
    //  DateTime <-> java.util.Date
    new DateTimeToDate, new DateToDateTime,
    //  DateTime <-> java.sql.Timestamp
    new DateTimeToTimestamp, new TimestampToDateTime,
    //  LocalDate <-> java.sql.Date
    new LocalDateToSqlDate, new SqlDateToLocalDate,
    //  DateTime -> LocalDate (unidirectional)
    new DateTimeToLocalDate
  )

  final class SqlDateToLocalDate extends CustomConverter[sql.Date, LocalDate] {
    override def convert(source: sql.Date, destinationType: Type[_ <: LocalDate]): LocalDate = {
      if (source == null) {
        null
      } else {
        new LocalDate(source.getTime, DateTimeZone.UTC)
      }
    }
  }


  final class LocalDateToSqlDate extends CustomConverter[LocalDate, sql.Date] {
    override def convert(source: LocalDate, destinationType: Type[_ <: sql.Date]): sql.Date = {
      if (source == null) {
        null
      } else {
        new sql.Date(source.toDate.getTime)
      }
    }
  }

  final class DateTimeToTimestamp extends CustomConverter[DateTime, Timestamp] {
    override def convert(source: DateTime, destinationType: Type[_ <: Timestamp]): Timestamp = {
      if (source == null) {
        null
      } else {
        new Timestamp(source.toDateTime(DateTimeZone.UTC).getMillis)
      }
    }
  }

  final class TimestampToDateTime extends CustomConverter[Timestamp, DateTime] {
    override def convert(source: Timestamp, destinationType: Type[_ <: DateTime]): DateTime = {
      if (source == null) {
        null
      } else {
        new DateTime(source.getTime, DateTimeZone.UTC)
      }
    }
  }

  final class DateTimeToDate extends CustomConverter[DateTime, Date] {
    override def convert(source: DateTime, destinationType: Type[_ <: Date]): Date = {
      if (source == null) {
        null
      } else {
        source.toDateTime(DateTimeZone.UTC).toDate
      }
    }
  }

  final class DateToDateTime extends CustomConverter[Date, DateTime] {
    override def convert(source: Date, destinationType: Type[_ <: DateTime]): DateTime = {
      if (source == null) {
        null
      } else {
        new DateTime(source.getTime, DateTimeZone.UTC)
      }
    }
  }

  final class DateTimeToLocalDate extends CustomConverter[DateTime, LocalDate] {
    override def convert(source: DateTime, destinationType: Type[_ <: LocalDate]): LocalDate = {
      if (source == null) {
        null
      } else {
        source.toDateTime(DateTimeZone.UTC).toLocalDate
      }
    }
  }

  final class LongToDateTime extends CustomConverter[java.lang.Long, DateTime] {
    override def convert(source: java.lang.Long, destinationType: Type[_ <: DateTime]): DateTime = {
      if (source == null) {
        null
      } else {
        new DateTime(source, DateTimeZone.UTC)
      }
    }
  }

  final class DateTimeToLong extends CustomConverter[DateTime, java.lang.Long] {
    override def convert(source: DateTime, destinationType: Type[_ <: java.lang.Long]): java.lang.Long = {
      if (source == null) {
        null.asInstanceOf[java.lang.Long]
      } else {
        source.toDateTime(DateTimeZone.UTC).getMillis.asInstanceOf[java.lang.Long]
      }
    }
  }

}
