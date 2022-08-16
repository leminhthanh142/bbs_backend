package infrastructure.src.main.scala.repositoryImpl.user
import dao.user.UserDao
import domain.src.main.scala.model.user.User
import domain.src.main.scala.repository.user.UserRepository
import dto.user.UserDto
import exceptions.AlreadyTakenField
import scalikejdbc.sqls
import skinny.orm.Alias
import utils.HashPasswordUtil.hashPassword
import valueObject.UserId

class UserRepositoryMysqlImpl() extends UserRepository {
  val user: Alias[User] = UserDao.defaultAlias

  override def save(userDto: UserDto): UserId = {
    val userByName = findByName(userDto.name.getOrElse(""))
    userByName match {
      case Some(user) => throw AlreadyTakenField(s"Username already exists!")
      case _ =>
    }

    val userByEmail = findByEmail(userDto.email.value)
    userByEmail match {
      case Some(user) => throw AlreadyTakenField(s"Email already exists!")
      case _ =>
    }

    UserDao.createWithAttributes(
      Symbol("name") -> userDto.name,
      Symbol("email") -> userDto.email.value,
      Symbol("password") -> hashPassword(userDto.password.value)
    )
  }

  override def findByEmail(email: String): Option[User] = {
    UserDao.findBy(sqls.eq(user.email, email))
  }

  override def findByName(name: String): Option[User] = {
    UserDao.findBy(sqls.eq(user.name, name))
  }
}

