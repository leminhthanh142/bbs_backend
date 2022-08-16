import com.google.inject.AbstractModule
import domain.src.main.scala.repository.post.PostRepository
import domain.src.main.scala.repository.user.UserRepository
import domain.src.main.scala.services.post.PostService
import domain.src.main.scala.services.user.UserService

import infrastructure.src.main.scala.repositoryImpl.post.PostRepositoryMysqlImpl
import infrastructure.src.main.scala.repositoryImpl.user.UserRepositoryMysqlImpl

import services.auth.{AuthService, AuthServiceImpl}
import services.file.{CSVService, CSVServiceImpl}
import services.post.PostServiceImpl
import services.user.UserServiceImpl

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[PostService]).to(classOf[PostServiceImpl])
    bind(classOf[PostRepository]).to(classOf[PostRepositoryMysqlImpl])
    bind(classOf[UserService]).to(classOf[UserServiceImpl])
    bind(classOf[UserRepository]).to(classOf[UserRepositoryMysqlImpl])
    bind(classOf[AuthService]).to(classOf[AuthServiceImpl])
    bind(classOf[CSVService]).to(classOf[CSVServiceImpl])
  }
}
