package controllers.restController.image

import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}

import java.io.File
import javax.inject.{Inject, Singleton}

@Singleton
class ImageController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  def getThumbnail(thumbnail: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok.sendFile(new File(s"public/images/$thumbnail"))(defaultExecutionContext, fileMimeTypes)
  }
}
