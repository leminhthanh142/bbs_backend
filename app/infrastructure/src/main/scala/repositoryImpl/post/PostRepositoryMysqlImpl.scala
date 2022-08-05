package infrastructure.src.main.scala.repositoryImpl.post
import dao.post.PostDao
import domain.src.main.scala.model.post.Post
import domain.src.main.scala.repository.post.PostRepository
import scalikejdbc.sqls
import skinny.Pagination
import skinny.orm.Alias

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
}
