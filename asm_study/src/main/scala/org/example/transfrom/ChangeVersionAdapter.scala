package org.example.transfrom

import org.example.GenericVisitor
import org.objectweb.asm.{ClassVisitor, Opcodes}

class ChangeVersionAdapter(cv:ClassVisitor, trace:Boolean) extends GenericVisitor(cv, trace){
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
    getCV.visit(Opcodes.V1_7, access, name, signature, superName, interfaces)


  /**
   * Here we just call visit function to change class's version to V1_7. Of course,
   * You can change the access or name argument in visitFiled and visitMethod methods
   * in cv, so that you generate a new filed or method with new access or name.
   */
  override def visitMethod(access: Int,
                           name: _root_.java.lang.String,
                           descriptor: _root_.java.lang.String,
                           signature: _root_.java.lang.String,
                           exceptions: Array[_root_.java.lang.String]): _root_.org.objectweb.asm.MethodVisitor =
    getCV.visitMethod(access, "Pre"+name, descriptor, signature, exceptions)
}
