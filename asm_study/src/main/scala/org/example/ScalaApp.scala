package org.example

/**
  * @author 
  */
object ScalaApp {
  val log = new org.example.utils.AgentLogger(this.getClass.getName)
  def printHello() =  log.info("Hello World from Scala")
  def main(args: Array[String]): Unit = {
    printHello()
  }
}