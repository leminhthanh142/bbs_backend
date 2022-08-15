package domain.src.main.scala.services.post

import domain.src.main.scala.model.post.Post
import dto.post.{Paged, PostCreateParams}
import valueObject.PostId

trait PostService {
  def getAllPostWithPagination(page: Int, size: Int): Paged[Post]
  def getPostCount: Long
  def getPostById(id: Int): Option[Post]
  def createPost(postCreateParams: PostCreateParams): PostId
}
