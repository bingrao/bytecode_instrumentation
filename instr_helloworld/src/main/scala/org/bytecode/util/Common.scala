package org.bytecode.util

trait Common {
  val logger = new AgentLogger(this.getClass.getName)
}
