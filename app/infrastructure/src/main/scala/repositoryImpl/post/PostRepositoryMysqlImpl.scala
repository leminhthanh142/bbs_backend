package infrastructure.src.main.scala.repositoryImpl.post
import dao.post.PostDao
import domain.src.main.scala.model.post.Post
import domain.src.main.scala.repository.post.PostRepository
import dto.post.PostCreateParams
import scalikejdbc.sqls
import skinny.Pagination
import skinny.orm.Alias
import valueObject.PostId

class PostRepositoryMysqlImpl extends PostRepository {
  val post: Alias[Post] = PostDao.defaultAlias
  override def getAllPostWithPagination(page: Int, size: Int): List[Post] =
    PostDao
      .where(sqls.isNull(post.deletedAt))
      .paginate(Pagination.page(page).per(size))
      .apply()

  override def getPostCount: Long = {
    PostDao.countBy(sqls.isNull(post.deletedAt))
  }

  override def getPostById(id: Int): Option[Post] = PostDao.findById(PostId(id))

  override def createPost(postCreateParams: PostCreateParams): PostId = {
    PostDao.createWithAttributes(
      Symbol("title") -> postCreateParams.title,
      Symbol("content") -> postCreateParams.content,
      Symbol("author") -> postCreateParams.author,
      Symbol("thumbnail") -> postCreateParams.thumbnail,
    )
  }
}
