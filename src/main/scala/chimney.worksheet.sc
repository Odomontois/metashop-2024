import java.time.ZonedDateTime
import java.util.UUID
import scala.deriving.Mirror
import scala.compiletime.ops.any.==
import scala.compiletime.{erasedValue, constValue}
import workshops.showCode

case class Client(
    name: String,
    tags: List[String],
    createDate: ZonedDateTime
)

case class ClientDTO(
    id: UUID,
    tags: List[String],
    name: String,
    createDate: ZonedDateTime,
    deleted: Boolean
)

val clientMirror = summon[Mirror.ProductOf[Client]]
val clientDTOMirror = summon[Mirror.ProductOf[ClientDTO]]

inline def transform[D1, D2](d1: D1): D2 = ???

val client = Client("Oleg", List("workshop"), ZonedDateTime.now())

val clientData = Tuple.fromProductTyped(client)
clientMirror.fromProductTyped(clientData) == client



type SelectField[Name <: String, NamesFrom <: Tuple, Fields <: Tuple] =
  NamesFrom match {
    case hname *: restNames =>
      Fields match {
        case hfield *: restFields =>
          (hname == Name) match {
            case true  => hfield
            case false => SelectField[Name, restNames, restFields]
          }
      }
  }

inline def selectFieldTail[name <: String, NamesFrom <: Tuple, Fields <: Tuple](
    fields: Fields
): SelectField[name, NamesFrom, Fields] =
  inline erasedValue[NamesFrom] match {
    case u: (hname *: restNames) =>
      inline fields match {
        case f1: (hfield *: restFields) =>
          val f2 = f1: (hfield *: restFields)
          inline erasedValue[hname == name] match {
            case _: true => f2.head
            case _: false =>
              selectFieldTail[name, restNames, restFields](f2.tail)
          }
      }
  }

import scala.compiletime.ops.int.+

type IndexOf[el, t <: Tuple] <: Int = t match {
  case h *: t =>
    (h == el) match {
      case true  => 0
      case false => 1 + IndexOf[el, t]
    }
}

type ClientNames = clientMirror.MirroredElemLabels
type ClientFields = clientMirror.MirroredElemTypes

selectFieldTail["name", ClientNames, ClientFields](clientData)
selectFieldTail["tags", ClientNames, ClientFields](clientData)
selectFieldTail["createDate", ClientNames, ClientFields](clientData)
// 1 + 2

showCode(selectFieldTail["createDate", ClientNames, ClientFields](clientData))

inline def selectField[
    name <: String,
    NamesFrom <: Tuple,
    Fields <: NonEmptyTuple
](
    fields: Fields
): Tuple.Elem[Fields, IndexOf[name, NamesFrom]] =
  fields.apply(constValue[IndexOf[name, NamesFrom]])

selectField["name", ClientNames, ClientFields](clientData)
selectField["tags", ClientNames, ClientFields](clientData)
selectField["createDate", ClientNames, ClientFields](clientData)

showCode(selectField["createDate", ClientNames, ClientFields](clientData))