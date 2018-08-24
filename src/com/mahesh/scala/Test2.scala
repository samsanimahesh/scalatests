package com.mahesh.scala

import scala.collection.mutable.ArrayBuffer
object Test2 {
  def main(args: Array[String]): Unit = {
    val str = "Peters_Car_AND_Licence_Number#ARE_4855_0545_8798_788"
//    str.split("#").foreach(_.split("_").foreach{println})
    val splitArray = str.split("#")
    var firstSplit = splitArray(0).split("_")
//    println("**************************************************************************")
//    firstSplit.indices.foreach(i=> if(i%2==1) println(firstSplit(i).toLowerCase) else println(firstSplit(i)))
//    println("**************************************************************************")

    var secondSplit = splitArray(1).split("_",2)
//    println("************************************************************************")
    var replaceRegex = "_".r
//    println("-------------------------------------------------------------------------")
    var secondSubSplit = replaceRegex.split(secondSplit(1))
    var newArray = new ArrayBuffer[String]
    newArray.append(secondSplit(0))
    for(i<-0 until (secondSubSplit.length,2)) {
      newArray.append(secondSubSplit(i) +"_"+secondSubSplit(i+1))
    }
//    newArray.foreach{println}
    firstSplit ++= newArray
    firstSplit.indices.foreach(i=> if(i%2==1) println(firstSplit(i).toLowerCase) else println(firstSplit(i)))
  }
  
}