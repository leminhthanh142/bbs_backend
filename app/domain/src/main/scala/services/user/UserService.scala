package domain.src.main.scala.services.user

import dto.user.UserDto
import valueObject.UserId

trait UserService {
  def save(userDto: UserDto): UserId
}
