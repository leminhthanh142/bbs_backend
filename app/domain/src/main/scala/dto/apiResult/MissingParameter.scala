package dto.apiResult

case class MissingParameter(message: String) extends ApiResult(message)