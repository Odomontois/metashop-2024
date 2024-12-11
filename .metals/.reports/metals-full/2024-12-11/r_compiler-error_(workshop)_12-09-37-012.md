file://<WORKSPACE>/src/main/scala/chimney.worksheet.sc
### dotty.tools.dotc.core.TypeError$$anon$1: Toplevel definition <error> is defined in
  <WORKSPACE>/src/main/scala/chimney.worksheet.sc
and also in
  <WORKSPACE>/src/main/scala/chimney.worksheet.sc
One of these files should be removed from the classpath.

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 493
uri: file://<WORKSPACE>/src/main/scala/chimney.worksheet.sc
text:
```scala
object worksheet{
  import java.time.ZonedDateTime
  import java.util.UUID
  import scala.deriving.Mirror
  
  case class Client(
      name: String,
      tags: List[Int],
      createDate: ZonedDateTime
  )
  
  case class ClientDTO(
      id: UUID,
      tags: List[Int],
      name: String,
      createDate: ZonedDateTime,
      deleted: Boolean
  )
  
  val clientMirror = summon[Mirror.ProductOf[Client]]
  val clientDTOMirror = summon[Mirror.ProductOf[ClientDTO]]  
  
  inline def tra@@
}
```



#### Error stacktrace:

```

```
#### Short summary: 

dotty.tools.dotc.core.TypeError$$anon$1: Toplevel definition <error> is defined in
  <WORKSPACE>/src/main/scala/chimney.worksheet.sc
and also in
  <WORKSPACE>/src/main/scala/chimney.worksheet.sc
One of these files should be removed from the classpath.