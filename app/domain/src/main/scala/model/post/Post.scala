package domain.src.main.scala.model.post

import org.joda.time.DateTime
import valueObject.PostId

case class Post(
                 id: PostId,
                 title: String,
                 content: String,
                 previewContent: String,
                 author: String,
                 thumbnail: String,
                 createdAt: DateTime,
                 updatedAt: DateTime,
                 deletedAt: DateTime
               )
