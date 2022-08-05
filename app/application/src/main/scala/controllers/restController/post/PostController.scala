package controllers.restController.post


import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}

@Singleton
class PostController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  def index: Action[AnyContent] = Action {
    Ok("ok")
  }
}
