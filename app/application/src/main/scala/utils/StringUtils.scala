package utils

object StringUtils {
  def textTruncate(value: String, number: Int): String = {
    value.slice(0, number)
  }
}
