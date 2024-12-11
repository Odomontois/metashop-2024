// compilation: Text -> 
// Stream of Token -> 
// ?(Abstract Syntax Tree)[Untyped, Unlinked] -[resolution]->
// -> ?AST[Untyped, Resolved] -[ 






    // typecheck [Scala 3 Metaprogramming ] 






// ] -> 
// AST[Typed] -> 
// (LIR, Bytecode, LLVM) -> 
//(Binary, Runtime)  

object Hello {

  //   (input, state) ->  [PROGRAM] -> (state, output)
  //   (input(program?)) -> [METAPROGRAM] -> (output(program?))
  def main(args: Array[String]) = 
    println(classOf[Foo].getMethods.toList)

  case class Foo() {
    def foo(x: Int): Int = 1
  }

  
}
