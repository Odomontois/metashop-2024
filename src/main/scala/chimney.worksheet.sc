import java.time.ZonedDateTime
import java.util.UUID
import scala.deriving.Mirror
import scala.compiletime.ops.any.==
import scala.compiletime.{erasedValue, constValue, summonInline}
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

inline def transform[A <: Product, B <: Product](
    a: A
)(using aMirror: Mirror.ProductOf[A], bMirror: Mirror.ProductOf[B]): B = {
  val aTuple = Tuple.fromProductTyped(a)(using aMirror)
  val bTuple = selectAllFields[
    bMirror.MirroredElemLabels,
    bMirror.MirroredElemTypes,
    aMirror.MirroredElemLabels,
    aMirror.MirroredElemTypes
  ](aTuple)
  bMirror.fromProduct(bTuple)
}

val client = Client("Oleg", List("workshop"), ZonedDateTime.now())
val clientDTO = ClientDTO(
  UUID.randomUUID(),
  List("workshop"),
  "Oleg",
  ZonedDateTime.now(),
  false
)
val clientData = Tuple.fromProductTyped(client)
val clientDTOData = Tuple.fromProductTyped(clientDTO)

clientMirror.fromProductTyped(clientData) == client

type SelectFieldRec[Name <: String, NamesFrom <: Tuple, Fields <: Tuple] =
  NamesFrom match {
    case hname *: restNames =>
      Fields match {
        case hfield *: restFields =>
          (hname == Name) match {
            case true  => hfield
            case false => SelectFieldRec[Name, restNames, restFields]
          }
      }
  }

inline def selectFieldTail[name <: String, NamesFrom <: Tuple, Fields <: Tuple](
    fields: Fields
): SelectFieldRec[name, NamesFrom, Fields] =
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

type ClientDTONames = clientDTOMirror.MirroredElemLabels
type ClientDTOFields = clientDTOMirror.MirroredElemTypes

selectFieldTail["name", ClientNames, ClientFields](clientData)
selectFieldTail["tags", ClientNames, ClientFields](clientData)
selectFieldTail["createDate", ClientNames, ClientFields](clientData)
// 1 + 2

showCode(selectFieldTail["createDate", ClientNames, ClientFields](clientData))

type SelectField[
    name,
    NamesFrom <: Tuple,
    Fields <: Tuple
] = Tuple.Elem[Fields, IndexOf[name, NamesFrom]]

inline def selectField[
    name,
    NamesFrom <: Tuple,
    Fields <: Tuple
](
    fields: Fields
): SelectField[name, NamesFrom, Fields] =
  fields.apply(constValue[IndexOf[name, NamesFrom]])

selectField["name", ClientNames, ClientFields](clientData)
selectField["tags", ClientNames, ClientFields](clientData)
selectField["createDate", ClientNames, ClientFields](clientData)

showCode(selectField["createDate", ClientNames, ClientFields](clientData))

inline def selectAllFields[
    NamesTo <: Tuple,
    FieldsTo <: Tuple,
    NamesFrom <: Tuple,
    FieldsFrom <: Tuple
](fields: FieldsFrom): FieldsTo =
  inline erasedValue[NamesTo] match {
    case _: (hname *: restNames) =>
      inline erasedValue[FieldsTo] match {
        case _: (hfield *: restFields) =>
          val construct = summonInline[(hfield *: restFields) <:< FieldsTo]
          type Fld = SelectField[hname, NamesFrom, FieldsFrom]
          val f1: Fld = selectField[hname, NamesFrom, FieldsFrom](fields)
          val hfield: hfield = summonInline[Fld <:< hfield](f1)
          val rest =
            selectAllFields[restNames, restFields, NamesFrom, FieldsFrom](
              fields
            )
          construct(hfield *: rest)
      }
    case _: EmptyTuple =>
      summonInline[EmptyTuple <:< FieldsTo](EmptyTuple)
  }

selectAllFields[ClientNames, ClientFields, ClientDTONames, ClientDTOFields](
  clientDTOData
)

transform[ClientDTO, Client](clientDTO)

1 + 2
