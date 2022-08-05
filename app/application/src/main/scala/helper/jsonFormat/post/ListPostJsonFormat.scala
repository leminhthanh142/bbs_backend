package helper.jsonFormat.post

import dto.post.ListPostResponse
import play.api.libs.json.{Json, Writes}
import PostJsonFormat._

object ListPostJsonFormat {

  implicit val listPostFormat: Writes[ListPostResponse] = (postList: ListPostResponse) => {
    Json.obj(
      "posts" -> postList.posts,
      "count" -> postList.count,
      "total" -> postList.total,
    )
  }
}
