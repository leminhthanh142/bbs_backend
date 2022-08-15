package services

import domain.src.main.scala.model.user.User
import domain.src.main.scala.repository.user.UserRepository
import dto.user.UserDto
import exceptions.{EntityNotFound, PasswordNotMatch}
import services.auth.AuthService
import utils.HashPasswordUtil.checkPassword

import javax.inject.{Inject, Singleton}

@Singleton
class AuthServiceImpl @Inject()(userRepository: UserRepository) extends AuthService {
  override def signIn(userDto: UserDto): User = {
    userRepository.findByEmail(userDto.email.value) match {
      case Some(user) =>
        if (checkPassword(userDto.password.value, user.password.value)) {
          user
        }
        else {
          throw new PasswordNotMatch
        }
      case _ => throw new EntityNotFound
    }
  }
}

