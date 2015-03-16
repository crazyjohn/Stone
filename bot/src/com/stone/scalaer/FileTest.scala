package com.stone.scalaer

import scala.io.Source

object FileTest extends App {
  if (args.length > 0) {
    for (line <- Source.fromFile(args(0)).getLines()) {
     print(line.length() + " " + line) 
    }
  } else {
    Console.err.println("Please enter filename")
  }
}