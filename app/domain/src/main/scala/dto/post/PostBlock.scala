package dto.post

case class PostBlock[A](id: String, blockType: String, data: A)
