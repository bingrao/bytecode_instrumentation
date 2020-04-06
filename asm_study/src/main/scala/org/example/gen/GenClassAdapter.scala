package org.example.gen

import org.example.utils.Common
import org.objectweb.asm.{ClassReader, ClassVisitor, ClassWriter, Opcodes}
import java.io.{File, FileOutputStream}
import org.example.GenericVisitor


class GenClassAdapter(cv:ClassVisitor, trace:Boolean) extends GenericVisitor(cv, trace) {
  override def visitEnd(): Unit = {
    import Opcodes._
    // visit method is called to create the class header
    getCV.visit(V1_8, // Specify Java version, 1.8

      // Specify Java modifiers, here specify that the class
      // is an public and abstract interface
      ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,

      // class name in internal form, no package or import
      // statements in compiled class, so class name should be fully qualified.
      "pkg/Comparable",

      // it means generics, null since the interface is not parameterized by
      // a type variable
      null,

      // Super class name in internal form. By default, interface classes
      // implicitly inherit from Object
      "java/lang/Object",

      // An array of interfaces that extended, names are in internal form
      //      Array("pkg/Mesurable"))
      null)

    // Generate interface a field and return FieldVisitor
    getCV.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, // Java modifiers bonding with fileds
      "less", // name as it appears in source code
      "I", // type of this field, in type descriptor form. int
      null, // generics
      // the field’s constant value,
      // it only use for a constant fields, such as final static fields;
      // for other fields, it must be null.
      new Integer(-1)
    ).visitEnd() // Since there is no annotations here, we call visistEnd to return FieldVisitor immediately

    getCV.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC,
      "equal", "I", null, new Integer(0)).visitEnd()
    getCV.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC,
      "greater", "I", null, new Integer(1)).visitEnd()

    // To define the compareTo method and return a [[MethodVisitor]], which can be used
    // to define the methods' annotations and attributes, and most importantly the
    // methods' code
    getCV.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, // Java modifiers for this method
      "compareTo", // name of method
      "(Ljava/lang/Object;)I", // descriptor of this method
      null, // generics

      // an array of the exceptions that can
      // be thrown by the method, specified in their interal name
      null
    ).visitEnd() // No annotations and return [[MethodVisitor]] inmediately by call visitEnd

    getCV.visitEnd()
  }
}

object GenClassAdapter extends Common{

  def gen_transform_with_reader = {
    val reader = new ClassReader("java.lang.Runnable")
    val writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS)
    // Create a new visitor bonding with existing writer
    val visitor = new GenClassAdapter(writer, false)
    reader.accept(visitor, ClassReader.SKIP_DEBUG)
    writer.toByteArray
  }

  def gen_transform_from_writer = {
    val writer = new ClassWriter(0)
    // Create a new visitor bonding with existing writer
    val visitor = new GenClassAdapter(writer, false)
    visitor.visitEnd()
    writer.toByteArray
  }
  def main(args: Array[String]): Unit = {
    val data = gen_transform_from_writer
    if (logger.isDebugEnabled) {
      val newFile = new File("./output/gen/Comparable.class")
      val newfout = new FileOutputStream(newFile)
      newfout.write(data)
      newfout.close()
    }
  }
}
