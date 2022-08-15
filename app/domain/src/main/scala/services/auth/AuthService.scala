package services.auth

import domain.src.main.scala.model.user.User
import dto.user.UserDto

trait AuthService {
  def signIn(userDto: UserDto): User
}
