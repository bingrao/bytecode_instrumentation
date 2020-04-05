package org.example.printer

import java.io.{File, FileOutputStream, PrintWriter}
import org.example.utils.Common
import org.objectweb.asm.{ClassReader, ClassWriter}

object PrinterDriver extends Common{


  def classPrint(bytes:Any):Array[Byte] = {

    val reader = bytes match {
      case bytes_array:Array[Byte] => {
        if (logger.isDebugEnabled) {
          val file_writer = new FileOutputStream(new File("old.class"))
          file_writer.write(bytes_array)
          file_writer.close()
        }
        new ClassReader(bytes_array)
      }
      case byte_str:String => {

        if (logger.isDebugEnabled) {
          val file_writer = new PrintWriter(new File("old.class"))
          file_writer.write(byte_str)
          file_writer.close()
        }
        new ClassReader(byte_str)
      }
      case _ => {
        logger.error(s"The input type is error: ${bytes}")
        new ClassReader("java.lang.Runnable")
      }
    }
    val writer = new ClassWriter(reader, 0)

    val visitor = new ClassPrinter(writer)

    reader.accept(visitor, 0)


    val data = writer.toByteArray
    if (logger.isDebugEnabled) {
      val newFile = new File("new.class")
      val newfout = new FileOutputStream(newFile)
      newfout.write(data)
      newfout.close()
    }
    data
  }
  def main(args: Array[String]): Unit = {
    classPrint("java.lang.Runnable")
  }


}
