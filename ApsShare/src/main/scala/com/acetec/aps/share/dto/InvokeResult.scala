package com.acetec.aps.share.dto

class InvokeResult[T](data_ : T, errorCode_ : Int, errorMessage_ : String) {
  var errorCode = 0
  var errorMessage = ""
  var data: T = _

  errorCode = errorCode_
  errorMessage = errorMessage_
  data = data_
}
