package com.acetec.aps.share

import com.acetec.aps.share.dto.{LotDto, ProductDto, TesterModelDto}

sealed trait Request

sealed trait Response

sealed trait Broadcast

case class ResponseWrapper[T](errorCode: Int, payload: T)

object LoginResultCode {
  val Success = 0
  val UsernameError = 1
  val PasswordError = 2
  val LoggedInError = 4 // 已经登陆
}

case class LoginRequest(username: String, password: String)

case class LoginResponse(resultCode: Int) extends Response

case class JoinRequest() extends Request

case class JoinResponse(member: String, allMembers: Seq[String]) extends Response

case class JoinBroadcast(member: String, allMembers: Seq[String]) extends Broadcast

case class LeaveRequest() extends Request

case class LeaveResponse(member: String, allMembers: Seq[String]) extends Response

case class LeaveBroadcast(member: String, allMembers: Seq[String]) extends Broadcast

case class ChatRequest(message: String) extends Request

case class ChatResponse(sender: String, message: String) extends Response

case class ChatBroadcast(sender: String, message: String) extends Broadcast


object FindAllProducts {


  val name = "product/findAll"

  case class Request()

  case class Response(products: List[ProductDto])

}

object FindAllLots {
  val name = "lot/findAll"

  case class Request()

  case class Response(lots: List[LotDto])

}

object Protocol {

  val FindAllTesterModels = "testerModel/findAll"

  case class FindAllTesterModelsRequest() extends Request

  case class FindAllTesterModelsResponse(testerModels: List[TesterModelDto]) extends Response

}