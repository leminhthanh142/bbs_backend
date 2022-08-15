package dto.post

case class Paged[A](
                    total: Long,
                    pageCount: Long,
                    items: List[A]
                )
