package serviceImpl.post

import domain.src.main.scala.model.post.Post
import domain.src.main.scala.repository.post.PostRepository
import domain.src.main.scala.services.post.PostService
import dto.post.PostCreateParams
import valueObject.PostId

import javax.inject.{Inject, Singleton}

@Singleton
class PostServiceImpl @Inject()(postRepository: PostRepository) extends PostService {
  override def getAllPostWithPagination(page: Int, size: Int): List[Post] = postRepository.getAllPostWithPagination(page, size)

  override def getPostCount: Long = postRepository.getPostCount

  override def getPostById(id: Int): Option[Post] = postRepository.getPostById(id)

  override def createPost(postCreateParams: PostCreateParams): PostId = postRepository.createPost(postCreateParams)
}
