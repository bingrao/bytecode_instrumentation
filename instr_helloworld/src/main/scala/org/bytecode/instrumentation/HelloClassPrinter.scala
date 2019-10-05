package org.bytecode.instrumentation

import scala.tools.asm.{ClassVisitor, ClassWriter, Opcodes}

class HelloClassPrinter(writer:ClassWriter) extends ClassVisitor(Opcodes.ASM5, writer) {
  override def visit(version: Int,
                     access: Int,
                     name: _root_.java.lang.String,
                     signature: _root_.java.lang.String,
                     superName: _root_.java.lang.String,
                     interfaces: Array[_root_.java.lang.String]): Unit = {
    println(name + " extends " + superName + " {")
    super.visit(version, access, name, signature, superName, interfaces)
  }
  override def visitMethod(access: Int,
                           name: _root_.java.lang.String,
                           desc: _root_.java.lang.String,
                           signature: _root_.java.lang.String,
                           exceptions: Array[_root_.java.lang.String]): _root_.scala.tools.asm.MethodVisitor = {
    println(" " + name + desc)
    super.visitMethod(access, name, desc, signature, exceptions)
  }

  override def visitEnd(): Unit = {
    println("}")
    super.visitEnd()
  }
}
