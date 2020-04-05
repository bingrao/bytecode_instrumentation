package org.example.transfrom

import org.example.utils.Common
import org.objectweb.asm.{ClassVisitor, Opcodes}

class TransformAdapter(writer:ClassVisitor)
  extends ClassVisitor(Opcodes.V1_8, writer)
    with Common{

}
