file://<WORKSPACE>/src/main/scala/tuples.worksheet.sc
### java.lang.OutOfMemoryError: Java heap space

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 1781
uri: file://<WORKSPACE>/src/main/scala/tuples.worksheet.sc
text:
```scala
object worksheet{
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
  
  def foo[x <: Int & S@@](x: x): x = x
  
  foo(1)
}
```



#### Error stacktrace:

```
dotty.tools.dotc.util.WeakHashSet.<init>(WeakHashSet.scala:54)
	dotty.tools.dotc.core.Uniques.<init>(Uniques.scala:11)
	dotty.tools.dotc.core.Contexts$ContextState.<init>(Contexts.scala:986)
	dotty.tools.dotc.core.Contexts$ContextBase.<init>(Contexts.scala:882)
	dotty.tools.dotc.Driver.initCtx(Driver.scala:66)
	dotty.tools.dotc.interactive.InteractiveDriver.<init>(InteractiveDriver.scala:34)
	dotty.tools.pc.CachingDriver.<init>(CachingDriver.scala:30)
	dotty.tools.pc.ScalaPresentationCompiler.$init$$$anonfun$1(ScalaPresentationCompiler.scala:85)
```
#### Short summary: 

java.lang.OutOfMemoryError: Java heap space