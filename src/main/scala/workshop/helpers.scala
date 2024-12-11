package workshops

import scala.quoted.{Expr, Type, Quotes, Varargs}

inline def showCode(inline code: Any): String = ${ showCodeImpl('code) }

private def showCodeImpl(x: Expr[Any])(using Quotes): Expr[String] = Expr(
  x.show
)
