package org.bytecode


import java.lang.instrument.Instrumentation

import org.bytecode.asm.AsmTransformer
import org.bytecode.javassist.JavassistTransformer
import org.bytecode.util.Common


/**
 * https://ivanyu.me/blog/2017/11/04/java-agents-javassist-and-byte-buddy/
 */
object Agent extends Common {

  /**
   *  If the agent is attached to an already running JVM,
   *  this method is invoked.
   *
   * @param args Agent command line arguments.
   * @param inst An object to access the JVM instrumentation mechanism.
   */
  def agentmain(args: String, inst: Instrumentation): Unit = {
    this.premain(args, inst)
  }

  /**
   *  If the agent is attached to a JVM on the start,
   *  this method is invoked before {@code main} method is called.
   * @param args agentArgs Agent command line arguments.
   * @param inst An object to access the JVM instrumentation mechanism.
   */
  def premain(args: String, inst: Instrumentation): Unit = {
    inst.addTransformer(new AsmTransformer)
//    inst.addTransformer(new JavassistTransformer)
  }
}
