package org.bytecode


import java.lang.instrument.Instrumentation

import org.bytecode.asm.AsmTransformer
import org.bytecode.javassist.JavassistTransformer
import org.bytecode.util.Common



object Agent extends Common {
  def premain(args: String, inst: Instrumentation): Unit = {
    inst.addTransformer(new AsmTransformer)
//    inst.addTransformer(new JavassistTransformer)
  }
}
