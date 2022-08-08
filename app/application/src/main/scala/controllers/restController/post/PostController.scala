package controllers.restController.post

import domain.src.main.scala.model.post.Post
import domain.src.main.scala.services.post.PostService
import dto.post.ListPostResponse
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}
import scala.util.{Failure, Success, Try}
import helper.jsonFormat.post.ListPostJsonFormat._
import helper.jsonFormat.post.PostJsonFormat._

@Singleton
class PostController @Inject()(val controllerComponents: ControllerComponents, postService: PostService) extends BaseController {
  def getAllPostWithPagination(page: Int, size: Int): Action[AnyContent] = Action {
    Try {
      val listPost: List[Post] = postService.getAllPostWithPagination(page, size)
      val totalPost: Long = postService.getPostCount
      ListPostResponse(
        posts = listPost,
        count = listPost.length,
        total = totalPost
      )
    }
    match {
      case Success(value) => Ok(Json.toJson(value))
      case Failure(exception) => BadRequest(exception.toString)
    }
  }

  def getPostById(id: Int): Action[AnyContent] = Action {
    Try {
      postService.getPostById(id)
    }
    match {
      case Success(value) => Ok(Json.toJson(value))
      case Failure(exception) => BadRequest(exception.toString)
    }
  }
}
