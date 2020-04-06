package org.example.printer

import java.io.{File, FileOutputStream, PrintWriter}

import org.example.GenericVisitor
import org.example.utils.Common
import org.objectweb.asm.{ClassReader, ClassVisitor, ClassWriter, Opcodes}

class ClassPrinterAdapter(cv:ClassVisitor, trace:Boolean) extends GenericVisitor(cv, trace) {
  override def visit(version: Int,
                     access: Int,
                     name: _root_.java.lang.String,
                     signature: _root_.java.lang.String,
                     superName: _root_.java.lang.String,
                     interfaces: Array[_root_.java.lang.String]): Unit = {
    logger.info(s"${name} extends ${superName} {")
    getCV.visit(version, access, name, signature, superName, interfaces)
  }

  override def visitField(access: Int,
                          name: _root_.java.lang.String,
                          descriptor: _root_.java.lang.String,
                          signature: _root_.java.lang.String, value: Any): _root_.org.objectweb.asm.FieldVisitor = {

    logger.info("\t" + descriptor + " " + name)
    getCV.visitField(access, name, descriptor, signature, value)
  }

  override def visitMethod(access: Int,
                           name: _root_.java.lang.String,
                           descriptor: _root_.java.lang.String,
                           signature: _root_.java.lang.String,
                           exceptions: Array[_root_.java.lang.String]): _root_.org.objectweb.asm.MethodVisitor = {

    logger.info("\t" + name + descriptor)
    getCV.visitMethod(access, name, descriptor, signature, exceptions)
  }

  override def visitEnd(): Unit = {
    logger.info("}")
    getCV.visitEnd()
  }
}
object ClassPrinterAdapter extends Common{
  def classPrint(bytes:Any):Array[Byte] = {
    val reader = bytes match {
      case bytes_array:Array[Byte] => {
        if (logger.isDebugEnabled) {
          val file_writer = new FileOutputStream(new File("old.class"))
          file_writer.write(bytes_array)
          file_writer.close()
        }
        new ClassReader(bytes_array)
      }
      case byte_str:String => {

        if (logger.isDebugEnabled) {
          val file_writer = new PrintWriter(new File("old.class"))
          file_writer.write(byte_str)
          file_writer.close()
        }
        new ClassReader(byte_str)
      }
      case _ => {
        logger.error(s"The input type is error: ${bytes}")
        new ClassReader("java.lang.Runnable")
      }
    }
    val writer = new ClassWriter(reader, 0)
    val visitor = new ClassPrinterAdapter(writer, false)
    reader.accept(visitor, 0)
    writer.toByteArray
  }
  def main(args: Array[String]): Unit = {
    val data = classPrint("java.lang.Runnable")
    if (logger.isDebugEnabled) {
      val newFile = new File("new.class")
      val newfout = new FileOutputStream(newFile)
      newfout.write(data)
      newfout.close()
    }
  }
}