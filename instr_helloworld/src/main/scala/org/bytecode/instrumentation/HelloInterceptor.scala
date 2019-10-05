package org.bytecode.instrumentation

object HelloInterceptor {
  def beforeInvoke() = {
    logging("Before: " + System.currentTimeMillis(),true)
  }
  def afterInvoke() = {
    logging("After: " + System.currentTimeMillis(),true)
  }
}
