package dto.post

import dto.apiResult.MissingParameter

case class PostCreateParams(
                           title: String,
                           content: String,
                           previewContent: String,
                           author: String,
                           thumbnail: String
                           ) {
  if (title.isEmpty) throw MissingParameter("Title is required")
  if (content.isEmpty) throw MissingParameter("Content is required")
  if (author.isEmpty) throw MissingParameter("Author name is required")
  if (thumbnail.isEmpty) throw MissingParameter("Thumbnail is required")
}