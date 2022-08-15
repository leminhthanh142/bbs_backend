package utils

import com.github.t3hnar.bcrypt._

object HashPasswordUtil {
  def hashPassword(password: String): String = password.bcryptBounded(generateSalt)
  def checkPassword(password: String, hashedPassword: String): Boolean = password.isBcryptedBounded(hashedPassword)
}

