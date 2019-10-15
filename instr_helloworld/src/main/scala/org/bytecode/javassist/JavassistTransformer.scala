package org.bytecode.javassist
import java.io.{File, FileOutputStream, ByteArrayInputStream}
import java.lang.instrument.{ClassFileTransformer, Instrumentation}
import java.security.ProtectionDomain

import javassist.{ClassPool, CtClass, LoaderClassPath}
import org.bytecode._

class JavassistTransformer extends ClassFileTransformer with util.Common {

  def transformerWithExistingClass(loader: ClassLoader,
                                   className: String,
                                   classfileBuffer: Array[Byte]):Array[Byte] = {
    if (instr_asm_class.equals(className)) {
      val cp = ClassPool.getDefault()
      val cc = cp.get(instr_javassist_class)
      if(cc != null) {
        val m = cc.getDeclaredMethod(instr_method)
        m.addLocalVariable("elapsedTime", CtClass.longType)
        m.insertBefore("elapsedTime = System.currentTimeMillis();")
        //        m.insertBefore("{ System.out.println(\"Hello.say():\"); }")
        m.insertAfter("{elapsedTime = System.currentTimeMillis() - elapsedTime;"
          + "System.out.println(\"Method Executed in ms: \" + elapsedTime);}")
        val byteCode = cc.toBytecode()
        cc.detach()
        byteCode
      }else{
        logger.info("There is no found class object")
        classfileBuffer
      }
    }else {
      classfileBuffer
    }
  }
  def transformerWithNewClass(loader: ClassLoader, className: String, classfileBuffer: Array[Byte]):Array[Byte] =  {
    if (instr_asm_class.equals(className)) {
      val classPool = new ClassPool()
      classPool.appendClassPath(new LoaderClassPath(loader))
      val byteArrayInputStream = new ByteArrayInputStream(classfileBuffer)
      val cc = classPool.makeClass(byteArrayInputStream)
      if (cc != null) {
        val m = cc.getDeclaredMethod(instr_method)
        m.addLocalVariable("elapsedTime", CtClass.longType)
        m.insertBefore("elapsedTime = System.currentTimeMillis();")
        //        m.insertBefore("{ System.out.println(\"Hello.say():\"); }")
        m.insertAfter("{elapsedTime = System.currentTimeMillis() - elapsedTime;"
          + "System.out.println(\"Method Executed in ms: \" + elapsedTime);}")
        val byteCode = cc.toBytecode()
        cc.detach()
        byteCode
      } else {
        logger.info("There is no found class object")
        classfileBuffer
      }
    } else {
      classfileBuffer
    }
  }
  override def transform(loader: ClassLoader,
                         className: String,
                         classBeingRedefined: Class[_],
                         protectionDomain: ProtectionDomain,
                         classfileBuffer: Array[Byte]): Array[Byte] = {
    try {
      if (className == null || className.isEmpty) {
        logger.error("Hit null or empty class name")
        return null
      }
      logger.debug("Transforming class using Javassist tool")
      return transformerWithExistingClass(loader, className, classfileBuffer)
    } catch {
      case ex: Throwable =>
        logger.warn("Failed to transform class " + className)
        return classfileBuffer
    }
  }
}
