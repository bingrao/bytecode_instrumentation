package org.bytecode.asm

import org.bytecode.util.Common

import scala.tools.asm.{ClassVisitor, ClassWriter, Opcodes}

class HelloClassPrinter(writer:ClassWriter) extends ClassVisitor(Opcodes.ASM5, writer) with Common{
  override def visit(version: Int,
                     access: Int,
                     name: _root_.java.lang.String,
                     signature: _root_.java.lang.String,
                     superName: _root_.java.lang.String,
                     interfaces: Array[_root_.java.lang.String]): Unit = {
    logger.debug(name + " extends " + superName + " {")
    super.visit(version, access, name, signature, superName, interfaces)
  }
  override def visitMethod(access: Int,
                           name: _root_.java.lang.String,
                           desc: _root_.java.lang.String,
                           signature: _root_.java.lang.String,
                           exceptions: Array[_root_.java.lang.String]): _root_.scala.tools.asm.MethodVisitor = {
    logger.info(" " + name + desc)
    super.visitMethod(access, name, desc, signature, exceptions)
  }

  override def visitEnd(): Unit = {
    logger.info("}")
    super.visitEnd()
  }
}
