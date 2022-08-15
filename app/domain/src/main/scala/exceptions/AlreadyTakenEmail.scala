package exceptions

case class AlreadyTakenEmail(message: String) extends Exception(message)
