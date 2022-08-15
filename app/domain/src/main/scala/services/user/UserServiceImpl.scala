package services.user

import domain.src.main.scala.repository.user.UserRepository
import domain.src.main.scala.services.user.UserService
import dto.user.UserDto
import valueObject.UserId

import javax.inject.{Inject, Singleton}

@Singleton
class UserServiceImpl @Inject()(userRepository: UserRepository) extends UserService {
  override def save(userDto: UserDto): UserId = userRepository.save(userDto)
}
