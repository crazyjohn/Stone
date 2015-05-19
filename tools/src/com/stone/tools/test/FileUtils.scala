package com.stone.tools.test

import java.io.PrintWriter
import java.io.File

object FileUtils {

  def withPrintWriter(file: File, op: PrintWriter => Unit) {
    val writer = new PrintWriter(file)
    try {
      op(writer)
    } finally {
      writer.close()
    }
  }

  /**
   * curring function, just like lang lib support;
   */
  def curriedWithPrintWriter(file: File)(op: PrintWriter => Unit) {
    val writer = new PrintWriter(file)
    try {
      op(writer)
    } finally {
      writer.close()
    }
  }

  def main(args: Array[String]) {
    FileUtils.curriedWithPrintWriter(new File("data.txt")) {
      writer => writer.println(new java.util.Date)
    }
  }
}