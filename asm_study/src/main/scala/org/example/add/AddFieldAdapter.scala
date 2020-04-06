package org.example.add

import org.example.GenericVisitor
import org.objectweb.asm.ClassVisitor

class AddFieldAdapter(cv:ClassVisitor, trace:Boolean) extends GenericVisitor(cv, trace) {
  val facc = 0
  val fName = ""
  val fDesc = ""
  var isFieldPresent = false

  override def visitField(access: Int,
                          name: _root_.java.lang.String,
                          descriptor: _root_.java.lang.String,
                          signature: _root_.java.lang.String,
                          value: Any): _root_.org.objectweb.asm.FieldVisitor = {
    if (name.equals(fName)){
      isFieldPresent = true
    }
    getCV.visitField(access, name, descriptor, signature, value)
  }

  override def visitEnd(): Unit = {
    if (isFieldPresent){
      val fv = cv.visitField(facc, fName, fDesc, null, null)
      if (fv != null){
        fv.visitEnd()
      }
    }
    getCV.visitEnd()
  }



}
