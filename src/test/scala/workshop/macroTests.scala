package workshop.macroTests

import workshop.{foo, lang, bar, selectField}
import workshop.Data

object check1 {
  bar(Data("Oleg", 30))

  @main def run = {
    val data = Data("Oleg", 30)
    println(selectField["age", Data](data))
    println(selectField["name", Data](data))
  }
}
