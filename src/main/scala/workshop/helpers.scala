package workshop

import scala.quoted.{Expr, Type, Quotes, Varargs}
import scala.compiletime.constValue

inline def showCode(inline code: Any): String = ${ showCodeImpl('code) }

private def showCodeImpl(x: Expr[Any])(using Quotes): Expr[String] = Expr(
  x.show
)

transparent inline def selectField[Name <: String, P <: Product](p: P): Any = ${
  selectFieldImpl[Name, P]('p)
}

private def selectFieldImpl[Name: Type, P: Type](
    p: Expr[P]
)(using q: Quotes): Expr[Any] = {
  import q.reflect.*
  val pTerm = p.asTerm
  val Pt = TypeRepr.of[P]
  val name = '{ constValue[Name] }.asExprOf[String].value match
    case Some(name) => name
    case None       => report.errorAndAbort("Expected a constant string", p)

  report.info(name)

  val fieldSymbol = Pt.classSymbol match {
    case Some(cs) => cs.fieldMember(name)
    case None     => report.errorAndAbort("Expected a case class", p)
  }
  val selected = Select(pTerm, fieldSymbol)
  selected.asExpr

}
