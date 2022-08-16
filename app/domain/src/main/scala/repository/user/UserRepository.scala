package domain.src.main.scala.repository.user

import domain.src.main.scala.model.user.User
import dto.user.UserDto
import valueObject.UserId

trait UserRepository {
  def findByEmail(email: String): Option[User]
  def findByName(name: String): Option[User]
  def save(userDto: UserDto): UserId
}
