val t: ("a", "b", "c") = ("a", "b", "c")

val t1: Tuple1["c"] = "c" *: EmptyTuple
val t2: ("b", "c") = "b" *: t1
val t3: ("a", "b", "c") = "a" *: t2

val ts: (String, String, String) =
  ("a": "a") *: ("b": "b") *: ("c": "c") *: EmptyTuple
val h: "a" = t.head

t.tail

def tupleSum(t: Tuple): Int =
  t match {
    case EmptyTuple => 0
    case t1: NonEmptyTuple =>
      t1.head match {
        case i: Int => i + tupleSum(t1.tail)
        case _      => tupleSum(t1.tail)
      }
  }

tupleSum((1, 2, 3, "123", 4))

@scala.annotation.tailrec
final def tupleSum2(t: Tuple, acc: Int = 0): Int =
  t match
    case EmptyTuple          => acc
    case (head: Int) *: tail => tupleSum2(tail, acc + head)
    case _ *: tail           => tupleSum2(tail, acc)

tupleSum2((1, 2, 3, 4))

import scala.compiletime.ops.int.+
import scala.compiletime.{constValueTuple, constValue}

type TupleSum[T <: Tuple] <: Int = T match {
  case EmptyTuple => 0
  case head *: tail =>
    head match {
      case Int => (head & Int) + TupleSum[tail]
      case _   => TupleSum[tail]
    }
}

def tupleSum3[T <: Tuple](x: T): TupleSum[T] = x match {
  case _: EmptyTuple => 0
  case t1: (head *: tail) =>
    val (h *: t) = (t1: (head *: tail))
    h match {
      case x: Int =>
        val y = (x + tupleSum3(t1.tail))
        y.asInstanceOf[(head & Int) + TupleSum[tail]]
      case _: Any => tupleSum3[tail](t)
    }
}

val x10: 10 = tupleSum3[(1, 2, 3, 4)]((1, 2, 3, 4))

constValueTuple[(1, 2, 3, 4)]

object uiu
constValueTuple[("a", true, 1)]
val y10: 10 = tupleSum3(constValueTuple[(1, 2, 3, 4)])

val x = constValue[10]

// def foo[x <: Int & Singleton](x: x): x = x

// foo(1)