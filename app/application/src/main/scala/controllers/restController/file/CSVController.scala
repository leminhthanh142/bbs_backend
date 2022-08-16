package controllers.restController.file

import domain.src.main.scala.services.post.PostService
import exceptions.EntityNotFound
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}
import services.file.CSVService

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}

@Singleton
class CSVController @Inject()(
  val controllerComponents: ControllerComponents,
  postService: PostService,
  csvService: CSVService
) extends BaseController {

  def makeCsv(id: Int): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Try {
      val post = postService.getPostById(id)
      csvService.makeCsv(post.get)
    }
    match {
      case Failure(exception) => exception match {
        case entityNotFound: EntityNotFound => NotFound(entityNotFound.message)
        case _ => InternalServerError("Something went wrong, please try again later!")
      }
      case Success(value) => Ok.sendFile(value)
    }
  }
}
