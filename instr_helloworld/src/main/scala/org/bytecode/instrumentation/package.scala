package org.bytecode

package object instrumentation {
  final val debug = false
  final val instr_asm_class  = "org/bytecode/test/JavaApp"
  final val instr_javassist_class  = "org.bytecode.test.JavaApp"
  final val instr_method = "printHello"
  def logging(log:Any, printout:Boolean = false) = if (printout || debug) println(log)
}
