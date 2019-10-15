package org.bytecode.instrumentation

import org.bytecode.util.Common

object HelloInterceptor extends Common  {
  def beforeInvoke() = {
    logger.info("Before: " + System.currentTimeMillis())
  }
  def afterInvoke() = {
    logger.info("After: " + System.currentTimeMillis())
  }
}
