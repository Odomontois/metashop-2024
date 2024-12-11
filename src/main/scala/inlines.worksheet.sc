import workshops.showCode
import scala.compiletime.{codeOf, constValueTuple}

def foo(x: Int): Int = x

showCode(foo(10) + foo(10))

codeOf(foo(10) + foo(10))

inline def foo: Int = 1
def bar: Int = 1

codeOf(foo)
codeOf(bar)

showCode(bar)
showCode(foo)

inline def abc: String = "abc"
inline def fed: String = "fed"
inline def abcfed: String = abc + fed

showCode(abcfed)

inline def tupleSum[T <: Tuple](t: T): Int =
  inline t match {
    case _: EmptyTuple => 0
    case t1: (Int *: tail) =>
      val t2 = t1: (Int *: tail)
      t2.head + tupleSum[tail](t2.tail)
  }

tupleSum(constValueTuple[(1, 2, 3, 4)])

showCode(tupleSum(constValueTuple[(1, 2, 3, 4)]))

showCode(tupleSum(constValueTuple[EmptyTuple]))

showCode(tupleSum(constValueTuple[Tuple1[1]]))