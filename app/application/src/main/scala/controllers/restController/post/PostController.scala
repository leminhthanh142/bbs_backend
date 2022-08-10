package controllers.restController.post

import domain.src.main.scala.model.post.Post
import domain.src.main.scala.services.post.PostService
import dto.apiResult.MissingParameter
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
      val thumbnail = postCreateParams.file("thumbnail").map {
        picture =>
          val imageName = Paths.get(picture.filename).getFileName.toString
          picture.ref.copyTo(new File(s"public/images/$imageName"), replace = true)
      }.head.getFileName.toString

      val title = postCreateParams.dataParts("title").head
      val content = postCreateParams.dataParts("content").head
      val previewContent = textTruncate(postCreateParams.dataParts("content").head, 50)
      val author = postCreateParams.dataParts("author").head

      val postParams = PostCreateParams(
        title,
        content,
        previewContent,
        author,
        thumbnail
      )

      postService.createPost(postParams)
    }
    match {
      case Failure(exception) => exception match {
        case error: MissingParameter => BadRequest(error.message)
        case _ => InternalServerError("Something went wrong, please try again later!")
      }
      case Success(_) => Created("Success")
    }
  }

  def getThumbnail(thumbnail: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok.sendFile(new File(s"public/images/$thumbnail"))(defaultExecutionContext, fileMimeTypes)
  }
}
