package org.example.load

import java.io.{File, FileOutputStream}

import org.example.utils.Common

object MyClassLoader extends ClassLoader with Common{

  override def findClass(name: String): Class[_] = {
    if(name.endsWith("Comparable")){
      val bytes = org.example.gen.GenClass.gen_class
      defineClass(name, bytes, 0, bytes.length)
    }
    super.findClass(name)
  }

  def defineClass(name:String, bytes:Array[Byte]) = {
    super.defineClass(name, bytes, 0, bytes.length)
  }
  def main(args: Array[String]): Unit = {
    val bytes = org.example.gen.GenClass.gen_class

    if (logger.isDebugEnabled) {
      val newFile = new File("./output/load/Comparable.class")
      val newfout = new FileOutputStream(newFile)
      newfout.write(bytes)
      newfout.close()
    }

    val c = this.defineClass("pkg.Comparable", bytes);

    logger.info(c.toString)
  }
}
