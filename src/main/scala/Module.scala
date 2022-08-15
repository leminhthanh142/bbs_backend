import com.google.inject.AbstractModule
import domain.src.main.scala.repository.post.PostRepository
import domain.src.main.scala.services.post.PostService
import infrastructure.src.main.scala.repositoryImpl.post.PostRepositoryMysqlImpl
import serviceImpl.post.PostServiceImpl

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[PostService]).to(classOf[PostServiceImpl])
    bind(classOf[PostRepository]).to(classOf[PostRepositoryMysqlImpl])
  }
}
