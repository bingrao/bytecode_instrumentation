package org.example.printer

import org.example.GenericVisitor
import org.objectweb.asm.{ClassVisitor, Opcodes}

class ClassPrinter(cv:ClassVisitor, trace:Boolean) extends GenericVisitor(cv, trace) {
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
