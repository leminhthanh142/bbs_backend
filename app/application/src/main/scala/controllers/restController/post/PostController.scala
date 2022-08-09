package controllers.restController.post

import domain.src.main.scala.model.post.Post
import domain.src.main.scala.services.post.PostService
import dto.post.{ListPostResponse, PostCreateParams}
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}

import javax.inject.{Inject, Singleton}
import scala.util.{Failure, Success, Try}
import helper.jsonFormat.post.ListPostJsonFormat._
import helper.jsonFormat.post.PostJsonFormat._
import utils.StringUtils.textTruncate

import java.io.File
import java.nio.file.Paths

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

  def createPost(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Try {
      val postCreateParams = request.body.asMultipartFormData.get
      var thumbnail = ""
      postCreateParams.file("thumbnail").map {
        picture =>
          thumbnail = Paths.get(picture.filename).getFileName.toString
          picture.ref.copyTo(new File(s"public/images/$thumbnail"), replace = true)
      }

      val postParams = PostCreateParams(
        title = postCreateParams.dataParts("title").mkString,
        content = postCreateParams.dataParts("content").mkString,
        previewContent = textTruncate(postCreateParams.dataParts("content").mkString, 50),
        author = postCreateParams.dataParts("author").mkString,
        thumbnail
      )

      postService.createPost(postParams)
    }
    match {
      case Failure(exception) => BadRequest(exception.toString)
      case Success(_) => Created("Success")
    }
  }

  def getThumbnail(thumbnail: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok.sendFile(new File(s"public/images/$thumbnail"))(defaultExecutionContext, fileMimeTypes)
  }
}
