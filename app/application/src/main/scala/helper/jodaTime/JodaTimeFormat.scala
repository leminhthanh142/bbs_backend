package helper.jodaTime

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

object JodaTimeFormat {
  def timeFormat(value: DateTime, format: String = "yyyy-MM-dd"): String = DateTimeFormat.forPattern(format).print(value)
}
