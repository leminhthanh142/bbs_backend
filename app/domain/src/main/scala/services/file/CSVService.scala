package services.file

import domain.src.main.scala.model.post.Post

import java.io.File

trait CSVService {
  def makeCsv(post: Post): File
}
