import play.api.mvc.{ActionBuilder, AnyContent, BodyParser, BodyParsers, Request, Result, Results, WrappedRequest}
import utils.jwt.JwtUtil.{TOKEN_REGEX, validateJwtToken}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

case class UserRequest[A](request: Request[A]) extends WrappedRequest[A](request)

class AuthAction @Inject()(parser: BodyParsers.Default)(implicit ec: ExecutionContext) extends ActionBuilder[UserRequest, AnyContent] {
  override def parser: BodyParser[AnyContent] = parser

  override protected def executionContext: ExecutionContext = ec

  def invokeBlock[A](request: Request[A], block: UserRequest[A] => Future[Result]): Future[Result] = {
    extractJwtToken(request) map { token =>
      if (validateJwtToken(token)) {
        block(UserRequest(request))
      }
      else Future.successful(Results.Unauthorized("unauthorized"))
    } getOrElse(Future.successful(Results.Unauthorized("unauthorized")))
  }

  private def extractJwtToken[A](request: Request[A]): Option[String] = {
    val accessToken = request.headers.get("Cookie") match {
      case Some(token) => token.replace(TOKEN_REGEX, "")
      case _ => ""
    }
    Some(accessToken)
  }
}