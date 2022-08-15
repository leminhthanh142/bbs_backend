package helper.jsonFormat.post

import dto.post.Paged
import play.api.libs.json.{Json, Writes}

object PagedJsonFormat {
  implicit def pagedJsonFormat[A](implicit writes: Writes[A]): Writes[Paged[A]] = (o: Paged[A]) => {
    Json.obj(
      "items" -> o.items,
      "total" -> o.total,
      "pageCount" -> o.pageCount
    )
  }
}