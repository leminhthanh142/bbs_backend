package dto.post

case class PostCreateParams(
                           title: String,
                           content: String,
                           previewContent: String,
                           author: String,
                           thumbnail: String
                           )