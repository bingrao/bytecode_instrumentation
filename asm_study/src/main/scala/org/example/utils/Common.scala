package org.example.utils

trait Common {
  val logger = new AgentLogger(this.getClass.getName)
}
