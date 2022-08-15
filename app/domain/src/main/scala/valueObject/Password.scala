package valueObject

case class Password(value: String) {
  lazy val passwordReg = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).{8,}$"
  if (value.isEmpty)
    throw new IllegalArgumentException("Password can not be empty!")
  if (!value.matches(passwordReg))
    throw new IllegalArgumentException("Invalid password!")
}

