package domain.src.main.scala.services.post

import domain.src.main.scala.model.post.Post

trait PostService {
  def getAllPostWithPagination(page: Int, size: Int): List[Post]
  def getPostCount: Long
  def getPostById(id: Int): Option[Post]
}
