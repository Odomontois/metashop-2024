import workshops.showCode

import scala.compiletime.{error, codeOf}
import workshop.{foo, bar, convert}
showCode(foo)

// bar

inline def quux(x: Int) = 
    error("error anyway, but x was " + codeOf(x))

// quux(2)

// convert[Int, String](2)
