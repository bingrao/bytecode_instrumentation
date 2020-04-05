package org.example.transfrom

import java.io.{File, FileOutputStream, PrintWriter}

import org.example.utils.Common
import org.objectweb.asm.{ClassReader, ClassVisitor, ClassWriter}

object TransDriver extends Common{

  /**
   *
   * @param name: class name
   * @param bytes: an array of bytes code for this class
   *
   *            ##########       ###########      ###########
   *            #        #       #         #      #         #
   *  bytes --> # Reader # -->   # Adapter # -->  # Writer  #
   *            #        #       #         #      #         #
   *            ##########       ###########      ###########
   */
  def adapter_driver(name:String, bytes:Array[Byte]): Unit = {
    val reader = new ClassReader(bytes)
    val writer = new ClassWriter(reader, 0)
    val Adapter = new TransformAdapter(writer)
    reader.accept(Adapter, 0)
    writer.toByteArray
  }

  def main(args: Array[String]): Unit = {

  }
}
