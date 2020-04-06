package org.example

import java.io.{File, FileOutputStream}
import java.lang.instrument.ClassFileTransformer
import org.example.utils.Common
import org.objectweb.asm.{ClassReader, ClassVisitor, ClassWriter}

/**
 *
 * @param newVisitor
 */
class GenericTransformer(newVisitor:ClassVisitor => GenericVisitor) extends ClassFileTransformer with Common {
  override def transform(loader: _root_.java.lang.ClassLoader,
                         className: _root_.java.lang.String,
                         classBeingRedefined: _root_.java.lang.Class[_],
                         protectionDomain: _root_.java.security.ProtectionDomain,
                         classfileBuffer: Array[Byte]): Array[Byte] = {

    try {
      if (className == null || className.isEmpty) {
        logger.info(s"Hit null or empty class name ${className}")
        classfileBuffer
      } else {
        logger.debug("Transforming class using ASM tool")


        if (logger.isDebugEnabled) {
          val oldFile = new File("old.class")
          val oldfout = new FileOutputStream(oldFile)
          oldfout.write(classfileBuffer)
          oldfout.close()
        }
        /**
         *
         * *
         * *            ##########       ###########      ###########
         * *            #        #       #         #      #         #
         * *  bytes --> # Reader # -->   # Adapter # -->  # Writer  #
         * *            #        #       #         #      #         #
         * *            ##########       ###########      ###########
         *
         *
         */
        val reader = new ClassReader(classfileBuffer)
        val writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS)

        // Create a new visitor bonding with existing writer
        val visitor = newVisitor(writer)

        reader.accept(visitor, ClassReader.SKIP_DEBUG)

        val reg = writer.toByteArray
        if (logger.isDebugEnabled) {
          val newFile = new File("new.class")
          val newfout = new FileOutputStream(newFile)
          newfout.write(reg)
          newfout.close()
        }
        reg
      }
    } catch {
      case ex: Throwable =>
        logger.warn("Failed to transform class " + className)
        return classfileBuffer
    }
  }
}
