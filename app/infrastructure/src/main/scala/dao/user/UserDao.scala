package dao.user

import domain.src.main.scala.model.user.User
import scalikejdbc.WrappedResultSet
import skinny.orm.{Alias, SkinnyCRUDMapperWithId}
import valueObject.{Email, Password, UserId}

object UserDao extends SkinnyCRUDMapperWithId[UserId, User]{
  override lazy val tableName = "user"

  override def defaultAlias: Alias[User] = createAlias("user")

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[User]): User = {
    User(
      id = UserId(rs.get(n.id)),
      name = rs.get(n.name),
      email = Email(rs.get(n.email)),
      password = Password(rs.get(n.password))
    )
  }

  override def idToRawValue(id: UserId): Any = id.value

  override def rawValueToId(value: Any): UserId = UserId(value.toString.toLong)
}
