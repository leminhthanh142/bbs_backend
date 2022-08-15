package helper.jsonFormat.post

import play.api.libs.json.{Json, Writes}
import PostJsonFormat._
import dto.post.ListPostResponse

object ListPostJsonFormat {

  implicit val listPostFormat: Writes[ListPostResponse] = (postList: ListPostResponse) => {
    Json.obj(
      "posts" -> postList.posts,
      "count" -> postList.count,
      "total" -> postList.total,
    )
  }
}
