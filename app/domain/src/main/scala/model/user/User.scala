package domain.src.main.scala.model.user

import valueObject.{Email, UserId, Password}

case class User(id: UserId, name: String, email: Email, password: Password)

