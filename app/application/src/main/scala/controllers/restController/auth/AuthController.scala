package controllers.restController.auth

import dto.user.UserDto
import exceptions.{EntityNotFound, PasswordNotMatch}
import play.api.libs.json.{JsResultException, Json}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Cookie, DiscardingCookie, Request}
import services.auth.AuthService
import utils.jwt.JwtUtil.generateJwtToken
import helper.jsonFormat.user.UserJsonFormat._

import javax.inject.Inject
import scala.util.{Failure, Success, Try}


class AuthController @Inject()(val controllerComponents: ControllerComponents, authService: AuthService) extends BaseController {
  def signIn(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Try {
      val payload: UserDto = request.body.asJson.get.as[UserDto]
      val user = authService.signIn(payload)
      val jwtToken = generateJwtToken(user)
      val cookie = Cookie("access_token", jwtToken)

      Ok(Json.toJson(user)).withCookies(cookie)
    }
    match {
      case Success(value) => value
      case Failure(exception) =>
        exception match {
          case _: PasswordNotMatch => Unauthorized("Password does not match")
          case _: EntityNotFound => Unauthorized("User not found")
          case jsExcept: JsResultException => BadRequest(jsExcept.errors.head._2.head.message)
          case error: Exception => {
            error.printStackTrace()
            InternalServerError("Something went wrong, please try again later!!")
          }
        }
    }
  }

  def signOut(): Action[AnyContent] = Action {
    Ok.discardingCookies(DiscardingCookie("access_token"))
  }
}

