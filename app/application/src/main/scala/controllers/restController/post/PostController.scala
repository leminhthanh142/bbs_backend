package controllers.restController.post

import domain.src.main.scala.services.post.PostService
import dto.post.PostCreateParams
import exceptions.{EntityNotFound, MissingParameter}
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}

import javax.inject.{Inject, Singleton}
import scala.util.{Failure, Success, Try}
import helper.jsonFormat.post.PostJsonFormat._
import helper.jsonFormat.post.PagedJsonFormat._
import play.api.libs.Files.TemporaryFile
import play.api.mvc.MultipartFormData.FilePart

import java.io.File
import java.nio.file.Paths

@Singleton
class PostController @Inject()(val controllerComponents: ControllerComponents, postService: PostService) extends BaseController {

  def getAllPostWithPagination(page: Int, size: Int): Action[AnyContent] = Action {
    Try {
      postService.getAllPostWithPagination(page, size)
    }
    match {
      case Success(value) => Ok(Json.toJson(value))
      case Failure(_) => InternalServerError("Something went wrong, please try again later!")
    }
  }

  def getPostById(id: Int): Action[AnyContent] = Action {
    Try {
      postService.getPostById(id)
    }
    match {
      case Success(value) => Ok(Json.toJson(value))
      case Failure(exception) => exception match {
        case _: EntityNotFound => NotFound("Can not find post")
        case _ => InternalServerError("Something went wrong, please try again later!")
      }
    }
  }

  def createPost(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Try {
      val postCreateParams = request.body.asMultipartFormData.get
      val thumbnail = postCreateParams.file("thumbnail")
        .map(uploadThumbnail)
        .getOrElse(throw MissingParameter("Missing thumbnail"))

      val title = postCreateParams.dataParts("title").head
      val content = postCreateParams.dataParts("content").head
      val author = postCreateParams.dataParts("author").head

      val postParams = PostCreateParams(
        title,
        content,
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

  def uploadThumbnail(file: FilePart[TemporaryFile]): String = {
    val imageName = Paths.get(file.filename).getFileName.toString
    file.ref.copyTo(new File(s"public/images/$imageName"), replace = true)
    imageName
  }

  def uploadThumbnailEditorJs(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val postCreateParams = request.body.asMultipartFormData.get
    val thumbnail = postCreateParams.file("image")
      .map(uploadThumbnail)
      .getOrElse(throw MissingParameter("Missing thumbnail"))

    Ok(Json.toJson(Json.obj(
      "success" -> 1,
      "file" -> Json.obj(
        "url" -> s"http://localhost:9000/api/thumbnails/$thumbnail"
      )))
    )
  }
}