package org.example.remove

import org.example.GenericVisitor
import org.objectweb.asm.{ClassVisitor}

class RemoveDebugAdapter(cv:ClassVisitor, trace:Boolean) extends GenericVisitor(cv, trace){

  /**
   * For example the following class adapter removes the information about outer
   * and inner classes, as well as the name of the source file from which the class
   * was compiled (the resulting class remains fully functional, because these elements
   * are only used for debugging purposes).
   */
  override def visitSource(source: String, debug: String): Unit ={

  }

  override def visitOuterClass(owner: _root_.java.lang.String,
                               name: _root_.java.lang.String,
                               descriptor: _root_.java.lang.String): Unit = {

  }

  override def visitInnerClass(name: _root_.java.lang.String,
                               outerName: _root_.java.lang.String,
                               innerName: _root_.java.lang.String,
                               access: Int): Unit = {

  }

  /**
   * But the above strategies do not work for removing fileds and methods, since
   * the their corresponding interfaces need to return a result.
   */

  override def visitMethod(access: Int,
                            name: _root_.java.lang.String,
                            descriptor: _root_.java.lang.String,
                            signature: _root_.java.lang.String,
                            exceptions: Array[_root_.java.lang.String]): _root_.org.objectweb.asm.MethodVisitor = {

    /**
     * There is a bunch of method events (may mutiple methods defined in a class) will
     * call this method, we just remove ones that we are interesting and keep others
     * in the source code.
     */
    if (name.equals("RDD") && descriptor.equals("[I]")){
      // Removing corresponding method by returning null object
      null
    } else {
      // do not delegate to next visitor -> this removes the method
      getCV.visitMethod(access, name, descriptor, signature, exceptions)
    }
  }

  override def visitField(access: Int,
                          name: _root_.java.lang.String,
                          descriptor: _root_.java.lang.String,
                          signature: _root_.java.lang.String,
                          value: Any): _root_.org.objectweb.asm.FieldVisitor = {
    if (name.endsWith("RDD"))
      // Removing corresponding filed by returning null object
      null
    else
      // do not delegate to next visitor -> this removes the filed
      getCV.visitField(access, name, descriptor, signature, value)
  }
}
