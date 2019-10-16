package org.bytecode.util

import  org.apache.log4j.Logger

class AgentLogger(name:String){
  private val logger = Logger.getLogger(name)

  def info(message:String) = logger.info(message)
  def warn(message:String) = logger.warn(message)
  def debug(message:String) = logger.debug(message)
  def error(message:String) = logger.error(message)

  def isDebugEnabled = logger.isDebugEnabled
}
