package org.example

import java.lang.instrument.Instrumentation

import org.example.transfrom.ChangeVersionAdapter
import org.objectweb.asm.ClassVisitor


/**
 *  The return can be stored on disk or loaded with a
 *  ClassLoader as described in gen package. But the transformation done
 *  inside a ClassLoader can only transform the classess loaded by this
 *  class loader.  If you want to make it affect to all classes you will
 *  have, you need to hook an instrumentation agent before loading a
 *  class to your classloader. Just like what we can do in the following.
 */


/**
 * https://ivanyu.me/blog/2017/11/04/java-agents-javassist-and-byte-buddy/
 */
object Agent extends utils.Common {

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
    inst.addTransformer(new GenericTransformer({
      case writer: ClassVisitor => new ChangeVersionAdapter(writer, false)
    }))
  }
}
