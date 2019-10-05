package org.bytecode.instrumentation

import scala.tools.asm._

class HelloClassAdapter(cv:ClassVisitor) extends ClassVisitor(Opcodes.ASM5, cv) {

  override def visit(version: Int,
                     access: Int,
                     name: _root_.java.lang.String,
                     signature: _root_.java.lang.String,
                     superName: _root_.java.lang.String,
                     interfaces: Array[_root_.java.lang.String]): Unit = {
    if (debug) println(s"The class is ${name}, its superName is ${superName}")
    super.visit(version, access, name, signature, superName, interfaces)
  }
  override def visitMethod(access: Int,
                           name: _root_.java.lang.String,
                           desc: _root_.java.lang.String,
                           signature: _root_.java.lang.String,
                           exceptions: Array[_root_.java.lang.String]): _root_.scala.tools.asm.MethodVisitor = {

    val mv = cv.visitMethod(access, name, desc, signature, exceptions)
    if ((mv != null) && (name.equals("printHello"))) new HelloMethod(this.api, mv) else mv
  }
}