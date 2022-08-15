package utils.jwt

import domain.src.main.scala.model.user.User
import io.jsonwebtoken.{Jwts, SignatureAlgorithm}

import java.time.Instant
import java.util.{Date, UUID}
import scala.util.{Failure, Success, Try}

object JwtUtil {
  val TOKEN_REGEX = "access_token="
  private val SECRET_KEY = "secretKey"

  def generateJwtToken(user: User): String = {
    Jwts
      .builder()
      .setId(UUID.randomUUID().toString)
      .setIssuedAt(Date.from(Instant.now()))
      .setExpiration(Date.from(Instant.now().plusSeconds(3600)))
      .setSubject(user.id.value.toString)
      .claim("email", user.email)
      .claim("user_name", user.name)
      .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
      .compact()
  }

  def validateJwtToken(jwtToken: String): Boolean = {
    Try {
      Jwts
        .parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(jwtToken.replace(TOKEN_REGEX, ""))
    } match {
      case Failure(_) => false
      case Success(_) => true
    }
  }
}
