package workshops

import scala.quoted.Quotes
import scala.quoted.Expr

inline def showCode(inline code: Any): String = ${ showCodeImpl('code) }

def showCodeImpl(x: Expr[Any])(using Quotes): Expr[String] = Expr(x.show)
