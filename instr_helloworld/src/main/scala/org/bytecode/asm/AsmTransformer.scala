package org.bytecode.asm
import org.bytecode.{debug,instr_asm_class}
import org.bytecode.util.Common
import java.io.{File, FileOutputStream}
import java.lang.instrument.{ClassFileTransformer, Instrumentation}
import java.security.ProtectionDomain
import scala.tools.asm.{ClassReader, ClassWriter}

class AsmTransformer extends ClassFileTransformer with Common {

  def agent_asm_HelloClassPrinter(className: String, bytes:Array[Byte]):Array[Byte] = {
    val reader = new ClassReader(bytes)
    val writer = new ClassWriter(reader, 0)
    val visitor = new HelloClassPrinter(writer)
    reader.accept(visitor, 0)
    return writer.toByteArray()
  }
  def agent_asm_HelloClassAdapter(className: String, bytes:Array[Byte]):Array[Byte] = {
    if (instr_asm_class.equals(className)) {
      //https://stackoverflow.com/questions/23416536/main-method-in-scala

      if (logger.isDebugEnabled) {
        val oldFile = new File("old.class")
        val oldfout = new FileOutputStream(oldFile)
        oldfout.write(bytes)
        oldfout.close()
      }
      val reader = new ClassReader(bytes)
      val writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS)
      val visitor = new HelloClassAdapter(writer)
      reader.accept(visitor, ClassReader.SKIP_DEBUG)
      val data = writer.toByteArray
      if (logger.isDebugEnabled) {
        val newFile = new File("new.class")
        val newfout = new FileOutputStream(newFile)
        newfout.write(data)
        newfout.close()
      }
      data
    } else {
      bytes
    }
  }

  override def transform(loader: ClassLoader,
                         className: String,
                         classBeingRedefined: Class[_],
                         protectionDomain: ProtectionDomain,
                         classfileBuffer: Array[Byte]): Array[Byte] = {
    try {
      if (className == null || className.isEmpty) {
        logger.info("Hit null or empty class name")
        return null
      }
      logger.debug("Transforming class using ASM tool")
      return agent_asm_HelloClassAdapter(className, classfileBuffer)
    } catch {
      case ex: Throwable =>
        logger.warn("Failed to transform class " + className)
        return classfileBuffer
    }
  }
}
