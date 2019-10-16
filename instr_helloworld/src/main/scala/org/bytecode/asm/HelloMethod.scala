package org.bytecode.asm

import org.bytecode.util.Common

import scala.tools.asm.{MethodVisitor, Opcodes}

class HelloMethod(api:Int, cv: MethodVisitor)  extends MethodVisitor(api,cv) with Common{

  override def visitCode(): Unit = {
    super.visitCode()
    this.visitMethodInsn(Opcodes.INVOKESTATIC, "org/bytecode/instrumentation/HelloInterceptor", "beforeInvoke", "()V",false)
  }

  override def visitInsn(opcode: Int): Unit = {
    if(opcode == Opcodes.RETURN) {
      this.visitMethodInsn(Opcodes.INVOKESTATIC, "org/bytecode/instrumentation/HelloInterceptor", "afterInvoke", "()V", false)
    }
    super.visitInsn(opcode)
  }
}
