import java.util.UUID
import scala.deriving.Mirror

def foo(x: Int): Int = {
  val y: x.type = x
  val z: x.type = y
  2
}

val x: 1 = 1
val y: x.type = 1

val z : x.type = 2 - 1

val u : x.type = 2 - x

val s : "aaaa" = "aaaa"


case class Game(id: UUID, name: String, anotherId: Option[UUID])

val m = summon[Mirror.ProductOf[Game]]

type Names = m.MirroredElemLabels
type Fields = m.MirroredElemTypes
