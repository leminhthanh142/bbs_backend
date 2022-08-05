package domain.src.main.scala.repository.post

import domain.src.main.scala.model.post.Post

trait PostRepository {
  def getAllPostWithPagination(page: Int, size: Int): List[Post]
  def getPostCount: Long
}
