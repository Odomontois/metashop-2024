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

inline def foo[A](x: Int): Data = ${ fooImpl[A]('x) }

def fooImpl[A: Type](x: Expr[Int])(using q: Quotes): Expr[Data] = {
  import q.reflect.*
  val data = Data("Oleg", 39)
  val me = Expr(data)
  val expr = '{
    class Foo

    (1, 2, 3)
  }

//   report.info(expr.asTerm.show(using Printer.TreeStructure))
//   expr.asTerm match {
//     case Inlined(_, _, t1) =>
//       report.info(t1.show(using Printer.TreeStructure))
//     case _ =>
//   }

  TypeRepr.of[A] match {
    case ConstantType(c) =>
      c.value match {
        case x: Int    => report.info(s"constant int: $x")
        case x: String => report.info(s"constant string: $x")
        case _         => report.info("other constant")
      }
    case _ =>
  }

//   report.info(s"name: ${data.name}")

//   report.info(s"age: ${data.age}")

  Type.of[A] match {
    case '[Int] =>
      report.info("I see integer")
    case '[(a, b)] =>
      summon[Type[a]]
      summon[Type[b]]
      summon[Type[List[(b, a, b)]]]
      report.info(s"I see tuple of ${Type.show[a]} and ${Type.show[b]}")
    case _ =>
      report.info(s"type is ${Type.show[A]}")

  }

  '{
    $me.addAge(10)
  }

}

inline def bar(x: Data): Unit = ${ barImpl('x) }

def barImpl(x: Expr[Data])(using q: Quotes): Expr[Unit] = {
  import q.reflect.*
  val expr = '{ $x.name }
  q.reflect.report.info(expr.show(using Printer.TreeStructure))
  '{}
}

inline def convert[A, B](x: A): B = ${ convertImpl[A, B]('x) }

def convertImpl[A: Type, B: Type](x: Expr[A])(using q: Quotes): Expr[B] =
  Try(x.asExprOf[B]).getOrElse(
    q.reflect.report.errorAndAbort(s"Expected ${Type.show[B]}, got: ${x.show}")
  )

object lang {
  def and(a: Boolean, b: Boolean): Boolean = ???
  def or(a: Boolean, b: Boolean): Boolean = ???
  def not(a: Boolean): Boolean = ???

  inline def apply(inline expr: Boolean): String =
    ${ parse('expr) }

  def parse(
      expr: Expr[Boolean]
  )(using q: Quotes): Expr[String] = {
    import q.reflect.*

    expr match {

      case '{ lang.and($a, $b) } =>
        '{ s"(${lang($a)} and ${lang($b)})" }
      case '{ lang.or($a, $b) } =>
        '{ s"(${lang($a)} or ${lang($b)})" }
      case '{ lang.not($a) } =>
        '{ s"not ${lang($a)}" }

      case _ =>
        '{
          ${
            Expr(
              expr.value.getOrElse(
                report.errorAndAbort("expecting a boolean constant", expr)
              )
            )
          }.toString
        }
    }
  }
}
