package dto.user

import valueObject.{Email, Password}

case class UserDto(name: Option[String], email: Email, password: Password)

