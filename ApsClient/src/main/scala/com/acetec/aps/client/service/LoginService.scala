package com.acetec.aps.client.service

import com.acetec.aps.share._
import rx.lang.scala.Observable
import rx.subjects.PublishSubject

trait LoginService {
  def login(username: String, password: String): Observable[LoginResponse]

  def me(): Observable[String]

  def loginResponse: PublishSubject[LoginResponse]
}
