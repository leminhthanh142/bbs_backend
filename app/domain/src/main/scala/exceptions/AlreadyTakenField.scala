package exceptions

case class AlreadyTakenField(message: String) extends Exception(message)
