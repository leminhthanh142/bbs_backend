package controllers.restController.user

import domain.src.main.scala.services.user.UserService
import dto.user.UserDto
import exceptions.AlreadyTakenEmail
import play.api.libs.json.JsResultException
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}

import javax.inject.{Inject, Singleton}
import scala.util.{Failure, Success, Try}
import helper.jsonFormat.user.UserJsonFormat._

@Singleton
class UserController @Inject()(val controllerComponents: ControllerComponents, userService: UserService) extends BaseController {
  def createUser(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Try {
      val payload = request.body.asJson.get.as[UserDto]
      userService.save(payload)
    }
    match {
      case Success(_) => Created("success")
      case Failure(exception) =>
        exception match {
          case error: AlreadyTakenEmail => Conflict(error.message)
          case jsExcept: JsResultException => BadRequest(jsExcept.errors.head._2.head.message)
          case th: Throwable => {
            th.printStackTrace()
            InternalServerError
          }
        }
    }
  }
}
