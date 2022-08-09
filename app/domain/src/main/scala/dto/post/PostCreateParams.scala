package dto.post

case class PostCreateParams(
                           title: String,
                           content: String,
                           previewContent: String,
                           author: String,
                           thumbnail: String
                           ) {
  if (title.isEmpty) throw new IllegalArgumentException("Title is required")
  if (content.isEmpty) throw new IllegalArgumentException("Content is required")
  if (previewContent.isEmpty) throw new IllegalArgumentException("Preview Content is required")
  if (author.isEmpty) throw new IllegalArgumentException("Author is required")
  if (thumbnail.isEmpty) throw new IllegalArgumentException("Thumbnail is required")
}
