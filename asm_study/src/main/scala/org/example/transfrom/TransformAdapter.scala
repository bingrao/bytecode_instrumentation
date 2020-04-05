package org.example.transfrom

import org.example.utils.Common
import org.objectweb.asm.{ClassVisitor, Opcodes}

class TransformAdapter(cv:ClassVisitor)
  extends ClassVisitor(Opcodes.V1_8, cv)
    with Common{
  /**
   *
   * This class overrides only one method of the ClassVisitor class. As a consequence
   * all calls are forwarded unchanged to the class visitor cv passed to the
   * constructor, except calls to the visit method, which are forwarded with a
   * modified class version number.
   */
  override def visit(version: Int,
                     access: Int,
                     name: _root_.java.lang.String,
                     signature: _root_.java.lang.String,
                     superName: _root_.java.lang.String,
                     interfaces: Array[_root_.java.lang.String]): Unit =
    cv.visit(Opcodes.V1_7, access, name, signature, superName, interfaces)
}
