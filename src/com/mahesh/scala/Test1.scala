package com.mahesh.scala

object Test1 {
  def main(args: Array[String]): Unit = {

    var regexStr = "(D|d)ad+".r
    var str = "My Dad has a dad who has a dad is Dada to me. My mom likes dad"
    regexStr.findAllIn(str).foreach{println}
    var replacedStr = str.replaceFirst("dad","mom")
    println(replacedStr)
    var dadRegexStr = "dad".r
    println(dadRegexStr.findAllIn(replacedStr).length)
    var momRegexStr = "mom".r
    println(momRegexStr.findAllIn(replacedStr).length)
  }
}
