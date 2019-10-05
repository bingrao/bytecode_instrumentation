package org.bytecode.instrumentation

object HelloInterceptor {
  def beforeInvoke() = {
    println("Before: " + System.currentTimeMillis())
  }
  def afterInvoke() = {
    println("After: " + System.currentTimeMillis())
  }
}
