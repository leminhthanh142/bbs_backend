package controllers.restController.user

import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}

@Singleton
class UserController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  def index: Action[AnyContent] = Action {
    Ok("ok")
  }
}
