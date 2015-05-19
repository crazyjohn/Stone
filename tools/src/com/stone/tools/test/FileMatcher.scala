package com.stone.tools.test

import java.io.File

object FileMatcher {
  private def filesHere = (new File(".")).listFiles()

  /**
   * file ending with query;
   */
  def filesEnding(query: String) = {
    filesMatching(query, (fileName: String, query: String) => fileName.endsWith(query))
    // filesMatching(query, _.endsWith(_))
  }

  def filesContaining(query: String) = {
    filesMatching(query, _.contains(_))
  }

  def filesMatching(query: String, matcher: (String, String) => Boolean) = {
    for (file <- filesHere; if matcher(file.getName, query))
      yield file
  }
}