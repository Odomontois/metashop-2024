package workshop

import scala.quoted.Expr
import scala.quoted.Quotes
import scala.quoted.ToExpr
import scala.util.Try
import scala.quoted.Type

// Typechecking
// ------------------
// Inline Functions
// ------------------
// Macro [Type TypeRepr, Expr]
// ------------------
// Macro [Tree]

// TASTy -> [ TASTy Macro ] -> TASTy

inline def asInt(inline x: String): Int = ${ asIntImpl('x) }

def asIntImpl(x: Expr[String])(using Quotes): Expr[Int] = {
  val one = Expr.apply(1)
  val two = Expr(2)

  '{
    0
  }
}
case class Data(name: String, age: Int) {
  def addAge(years: Int): Data = copy(age = age + years)
}
object Data {
  given ToExpr[Data] with {
    def apply(data: Data)(using Quotes): Expr[Data] = '{
      Data(${ Expr(data.name) }, ${ Expr(data.age) })
    }
  }
}

inline def foo: Data = ${ fooImpl }

def fooImpl(using Quotes): Expr[Data] = {
  val me = Expr(Data("Oleg", 39))

  '{
    $me.addAge(10)
  }

}

inline def bar: Data = ${ barImpl }

def barImpl(using q: Quotes): Expr[Data] = {
  val me = Expr("Oleg")

  Try(me.asExprOf[Data]).getOrElse(
    q.reflect.report.errorAndAbort(s"Expected Data, got: ${me.show}")
  )
}

inline def convert[A, B](x: A): B = ${ convertImpl[A, B]('x) }

def convertImpl[A: Type, B: Type](x: Expr[A])(using q: Quotes): Expr[B] =
  Try(x.asExprOf[B]).getOrElse(
    q.reflect.report.errorAndAbort(s"Expected ${Type.show[B]}, got: ${x.show}")
  )
