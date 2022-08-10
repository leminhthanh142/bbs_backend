package dto.post

import org.joda.time.DateTime

case class PostContent(time: DateTime, version: String, blocks: List[PostBlock])
