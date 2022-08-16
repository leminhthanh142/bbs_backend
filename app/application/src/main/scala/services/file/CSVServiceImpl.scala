package services.file
import au.com.bytecode.opencsv.CSVWriter
import domain.src.main.scala.model.post.Post

import java.io.{FileWriter, File}
import java.util
import javax.inject.Singleton
import helper.jodaTime.JodaTimeFormat._

@Singleton
class CSVServiceImpl extends CSVService {
  override def makeCsv(post: Post): File = {
    val fileName = post.title
    val path = s"src/resources/csv/$fileName.csv"
    val csvWriter = new CSVWriter(new FileWriter(path))
    val fields: Array[String] = Array("Author name", "Title", "Created at", "Updated at")
    val record = new util.ArrayList[Array[String]]()
    record.add(fields)
    record.add(Array(
      post.author,
      post.title,
      timeFormat(post.updatedAt),
      timeFormat(post.createdAt),
    ))
    csvWriter.writeAll(record)
    csvWriter.close()
    new File(path)
  }
}
