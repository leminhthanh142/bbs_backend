package helper.jsonFormat.user

import domain.src.main.scala.model.user.User
import dto.user.UserDto
import play.api.libs.json.{JsValue, Json, Reads, Writes}
import valueObject.{Email, Password}

object UserJsonFormat {
  implicit val formatUserDto: Reads[UserDto] = (js: JsValue) => {
    for {
      name <- (js \ "name").validateOpt[String]
      email <- (js \ "email").validate[String]
      password <- (js \ "password").validate[String]
    } yield UserDto(name, Email(email), Password(password))
  }

  implicit val formatUser: Writes[User] = (user: User) => {
    Json.obj(
      "id" -> user.id.value,
      "name" -> user.name,
      "email" -> user.email.value
    )
  }
}

