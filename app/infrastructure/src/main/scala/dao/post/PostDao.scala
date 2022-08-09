package dao.post

import domain.src.main.scala.model.post.Post
import org.joda.time.DateTime
import scalikejdbc.WrappedResultSet
import skinny.orm.{Alias, SkinnyCRUDMapperWithId}
import valueObject.PostId

object PostDao extends SkinnyCRUDMapperWithId[PostId, Post] {
  override lazy val tableName = "post"
  override def defaultAlias: Alias[Post] = createAlias("post")

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[Post]): Post = {
    Post(
      id = PostId(rs.get(n.id)),
      title = rs.get(n.title),
      content = rs.get(n.content),
      previewContent = rs.get(n.previewContent),
      author = rs.get(n.author),
      thumbnail = rs.get(n.thumbnail),
      createdAt = DateTime.now(),
      updatedAt = DateTime.now(),
      deletedAt = rs.get(n.deletedAt)
    )
  }

  override def idToRawValue(id: PostId): Any = id.value

  override def rawValueToId(value: Any): PostId = PostId(value.toString.toLong)
}
