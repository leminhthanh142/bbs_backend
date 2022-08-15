package valueObject

case class Email(value: String) {
  lazy val emailReg = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$"
  if (value.isEmpty)
    throw new IllegalArgumentException("Email can not be empty!")
  if (!value.matches(emailReg))
    throw new IllegalArgumentException("Invalid Email!")
}
