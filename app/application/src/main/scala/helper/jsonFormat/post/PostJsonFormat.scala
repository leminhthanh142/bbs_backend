package helper.jsonFormat.post

import domain.src.main.scala.model.post.Post
import helper.jodaTime.JodaTimeFormat.timeFormat
import play.api.libs.json.{Json, Writes}

object PostJsonFormat {
  implicit val postFormat: Writes[Post] = (post: Post) => {
    Json.obj(
      "id" -> post.id.value,
      "title" -> post.title,
      "content" -> post.content,
      "author" -> post.author,
      "thumbnail" -> post.thumbnail,
      "createdAt" -> timeFormat(post.createdAt),
      "updatedAt" -> timeFormat(post.updatedAt),
    )
  }
}
