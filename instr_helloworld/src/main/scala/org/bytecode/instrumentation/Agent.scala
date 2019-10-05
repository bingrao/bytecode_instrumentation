package org.bytecode.instrumentation

import java.io.{File, FileOutputStream}
import java.lang.instrument.{ClassFileTransformer, Instrumentation}
import java.security.ProtectionDomain

import javassist.{ClassPool, CtClass}

import scala.tools.asm.{ClassReader, ClassWriter}

object Agent {
  def premain(args: String, inst: Instrumentation): Unit = {

    def agent_javassist():Array[Byte]  = {
      val cp = ClassPool.getDefault()
      val cc = cp.get(instr_javassist_class)
      if(cc != null) {
        val m = cc.getDeclaredMethod(instr_method)
        m.addLocalVariable("elapsedTime", CtClass.longType)
        m.insertBefore("elapsedTime = System.currentTimeMillis();")
        //        m.insertBefore("{ Console.println(\"Hello.say():\"); }")  // Cannot find Console or println
        m.insertAfter("{elapsedTime = System.currentTimeMillis() - elapsedTime;"
          + "println(\"Method Executed in ms: \" + elapsedTime);}")
        val byteCode = cc.toBytecode()
        cc.detach()
        return byteCode
      }else{
        println("There is no found class object")
        return null
      }
    }


    def agent_asm_HelloClassPrinter(bytes:Array[Byte]):Array[Byte] = {
      val reader = new ClassReader(bytes)
      val writer = new ClassWriter(reader, 0)
      val visitor = new HelloClassPrinter(writer)
      reader.accept(visitor, 0)
      return writer.toByteArray()
    }


    def agent_asm_HelloClassAdapter(bytes:Array[Byte]):Array[Byte] = {
      //https://stackoverflow.com/questions/23416536/main-method-in-scala
      if (debug) {
        val oldFile = new File("old.class")
        val oldfout = new FileOutputStream(oldFile)
        oldfout.write(bytes)
        oldfout.close()
      }
      val reader = new ClassReader(bytes)
      val writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS)
      val visitor = new HelloClassAdapter(writer)
      reader.accept(visitor, ClassReader.SKIP_DEBUG)
      val data = writer.toByteArray

      if (debug) {
        val newFile = new File("new.class")
        val newfout = new FileOutputStream(newFile)
        newfout.write(data)
        newfout.close()
      }
      return data
    }


    inst.addTransformer(new ClassFileTransformer {
      override def transform(loader: ClassLoader,
                             className: String,
                             classBeingRedefined: Class[_],
                             protectionDomain: ProtectionDomain,
                             classfileBuffer: Array[Byte]): Array[Byte] = {

        if (debug)  println("The class name is: " + className)
        if (instr_asm_class.equals(className)) {
          try {
            agent_asm_HelloClassAdapter(classfileBuffer)
          } catch {
            case ex:Exception =>
              ex.printStackTrace()
              null
          }
        } else null
      }
    })
  }
}
