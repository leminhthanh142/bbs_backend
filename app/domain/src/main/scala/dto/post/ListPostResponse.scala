package dto.post

import domain.src.main.scala.model.post.Post

case class ListPostResponse(
                             posts: List[Post],
                             count: Int,
                             total: Long
                           )
