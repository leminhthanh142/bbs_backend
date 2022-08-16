package infrastructure.src.main.scala.repositoryImpl.user
import dao.user.UserDao
import domain.src.main.scala.model.user.User
import domain.src.main.scala.repository.user.UserRepository
import dto.user.UserDto
import exceptions.AlreadyTakenEmail
import scalikejdbc.sqls
import skinny.orm.Alias
import utils.HashPasswordUtil.hashPassword
import valueObject.UserId

class UserRepositoryMysqlImpl() extends UserRepository {
  val user: Alias[User] = UserDao.defaultAlias

  override def save(userDto: UserDto): UserId = {
    val user = findByEmail(userDto.email.value)
    user match {
      case Some(user) => throw AlreadyTakenEmail(s"Email has been taken, please try again with a new one!")
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
}

