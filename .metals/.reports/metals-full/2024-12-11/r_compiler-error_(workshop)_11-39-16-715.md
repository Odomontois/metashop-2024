file://<WORKSPACE>/src/main/scala/tuples.worksheet.sc
### java.lang.AssertionError: NoDenotation.owner

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
uri: file://<WORKSPACE>/src/main/scala/tuples.worksheet.sc
text:
```scala
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
import scala.compiletime.{constValueTuple}

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
    val (h *: t): (head *: tail) = t1
    hmatch {
      case x: Int =>
        val y = (x + tupleSum3(t1.tail))
        y.asInstanceOf[(head & Int) + TupleSum[tail]]
      case _: Any => tupleSum3[tail](t2.tail)
    }
}

val x10: 10 = tupleSum3[(1, 2, 3, 4)]((1, 2, 3, 4))

constValueTuple[(1, 2, 3, 4)]

object uiu
constValueTuple[("a", true, 1)]
val y10: 10 = tupleSum3(constValueTuple[(1, 2, 3, 4)])

```



#### Error stacktrace:

```
dotty.tools.dotc.core.SymDenotations$NoDenotation$.owner(SymDenotations.scala:2623)
	dotty.tools.dotc.core.SymDenotations$SymDenotation.isSelfSym(SymDenotations.scala:718)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:322)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1801)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1801)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.fold$1(Trees.scala:1665)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.apply(Trees.scala:1667)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.foldOver(Trees.scala:1698)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.traverseChildren(Trees.scala:1802)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:443)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1801)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1801)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.foldOver(Trees.scala:1706)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.traverseChildren(Trees.scala:1802)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:443)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1801)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1801)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.fold$1(Trees.scala:1665)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.apply(Trees.scala:1667)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.foldOver(Trees.scala:1704)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.traverseChildren(Trees.scala:1802)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:443)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse$$anonfun$13(ExtractSemanticDB.scala:383)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.immutable.List.foreach(List.scala:334)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:378)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1801)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1801)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.foldOver(Trees.scala:1698)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.traverseChildren(Trees.scala:1802)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:443)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1801)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1801)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.foldOver(Trees.scala:1706)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.traverseChildren(Trees.scala:1802)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:443)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1801)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1801)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.fold$1(Trees.scala:1665)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.apply(Trees.scala:1667)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.foldOver(Trees.scala:1704)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.traverseChildren(Trees.scala:1802)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:443)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:403)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1801)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1801)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.foldOver(Trees.scala:1686)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.traverseChildren(Trees.scala:1802)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:430)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1801)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1801)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.foldOver(Trees.scala:1753)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.traverseChildren(Trees.scala:1802)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:346)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse$$anonfun$11(ExtractSemanticDB.scala:369)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.immutable.List.foreach(List.scala:334)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:369)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1801)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.apply(Trees.scala:1801)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.foldOver(Trees.scala:1757)
	dotty.tools.dotc.ast.Trees$Instance$TreeAccumulator.foldOver(Trees.scala:1671)
	dotty.tools.dotc.ast.Trees$Instance$TreeTraverser.traverseChildren(Trees.scala:1802)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:343)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse$$anonfun$1(ExtractSemanticDB.scala:307)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.immutable.List.foreach(List.scala:334)
	dotty.tools.dotc.semanticdb.ExtractSemanticDB$Extractor.traverse(ExtractSemanticDB.scala:307)
	dotty.tools.pc.SemanticdbTextDocumentProvider.textDocument(SemanticdbTextDocumentProvider.scala:36)
	dotty.tools.pc.ScalaPresentationCompiler.semanticdbTextDocument$$anonfun$1(ScalaPresentationCompiler.scala:242)
```
#### Short summary: 

java.lang.AssertionError: NoDenotation.owner