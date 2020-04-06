package org.example.chain

import org.example.GenericVisitor
import org.objectweb.asm.{ClassVisitor, Opcodes}


/**
 *
 * @param cvs: An chain of independent ClassVisitors to do complex transformations.
 *  Note also that a transformation chain is not neccessarily linear.
 *
 *
 */

class MultiClassAdapter(cvs:Array[GenericVisitor]) extends ClassVisitor(Opcodes.ASM5) {
  /**
   * In a ClassVisitor, visit and visitEnd are called exactly once, but visitFiled and
   * visitMethod are called multiple times maybe.
   */
  override def visit(version: Int,
                     access: Int,
                     name: _root_.java.lang.String,
                     signature: _root_.java.lang.String,
                     superName: _root_.java.lang.String,
                     interfaces: Array[_root_.java.lang.String]): Unit = {

    for (cv <- cvs)
      cv.getCV.visit(version, access, name, signature, superName, interfaces)
  }
}
