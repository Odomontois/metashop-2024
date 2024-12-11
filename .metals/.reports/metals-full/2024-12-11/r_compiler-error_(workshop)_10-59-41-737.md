file://<WORKSPACE>/src/main/scala/tuples.worksheet.sc
### scala.MatchError: TypeDef(TupleSum,LambdaTypeTree(List(TypeDef(T,TypeBoundsTree(EmptyTree,EmptyTree,EmptyTree))),MatchTypeTree(EmptyTree,Ident(T),List(CaseDef(Ident(EmptyTuple),EmptyTree,SingletonTypeTree(Literal(Constant(0)))))))) (of class dotty.tools.dotc.ast.Trees$TypeDef)

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 929
uri: file://<WORKSPACE>/src/main/scala/tuples.worksheet.sc
text:
```scala
object worksheet{
  val t: ("a", "b", "c") = ("a", "b", "c")
  
  val t1: Tuple1["c"] = "c" *: EmptyTuple
  val t2: ("b", "c") = "b" *: t1
  val t3: ("a", "b", "c") = "a" *: t2
  
  val ts: (String, String, String) = ("a" : "a") *: ("b" : "b") *: ("c" : "c") *: EmptyTuple
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
          case EmptyTuple => acc
          case (head: Int) *: tail => tupleSum2(tail, acc + head)
          case _ *: tail => tupleSum2(tail, acc)
  
  tupleSum2((1, 2, 3, 4))
  
  
  type TupleSum[T] = T match 
      case EmptyTuple => 0
      c@@
  
  
}
```



#### Error stacktrace:

```
dotty.tools.pc.completions.KeywordsCompletions$.checkTemplateForNewParents$$anonfun$2(KeywordsCompletions.scala:218)
	scala.Option.map(Option.scala:242)
	dotty.tools.pc.completions.KeywordsCompletions$.checkTemplateForNewParents(KeywordsCompletions.scala:215)
	dotty.tools.pc.completions.KeywordsCompletions$.contribute(KeywordsCompletions.scala:44)
	dotty.tools.pc.completions.Completions.completions(Completions.scala:126)
	dotty.tools.pc.completions.CompletionProvider.completions(CompletionProvider.scala:135)
	dotty.tools.pc.ScalaPresentationCompiler.complete$$anonfun$1(ScalaPresentationCompiler.scala:150)
```
#### Short summary: 

scala.MatchError: TypeDef(TupleSum,LambdaTypeTree(List(TypeDef(T,TypeBoundsTree(EmptyTree,EmptyTree,EmptyTree))),MatchTypeTree(EmptyTree,Ident(T),List(CaseDef(Ident(EmptyTuple),EmptyTree,SingletonTypeTree(Literal(Constant(0)))))))) (of class dotty.tools.dotc.ast.Trees$TypeDef)