file://<WORKSPACE>/src/main/scala/singletons.worksheet.sc
### dotty.tools.dotc.core.TypeError$$anon$1: Toplevel definition <error> is defined in
  <WORKSPACE>/src/main/scala/singletons.worksheet.sc
and also in
  <WORKSPACE>/src/main/scala/singletons.worksheet.sc
One of these files should be removed from the classpath.

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 408
uri: file://<WORKSPACE>/src/main/scala/singletons.worksheet.sc
text:
```scala
object worksheet{
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
  
  type N@@
  
}
```



#### Error stacktrace:

```

```
#### Short summary: 

dotty.tools.dotc.core.TypeError$$anon$1: Toplevel definition <error> is defined in
  <WORKSPACE>/src/main/scala/singletons.worksheet.sc
and also in
  <WORKSPACE>/src/main/scala/singletons.worksheet.sc
One of these files should be removed from the classpath.