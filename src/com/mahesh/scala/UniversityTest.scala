package com.mahesh.scala

import scala.collection.mutable.Map
import scala.collection.mutable.ListBuffer

object UniversityTest extends App{
  
  var universityMap = Map.empty[String,University]
  
  var universityToStudentsMap:Map[String, ListBuffer[Student]] = Map.empty[String,ListBuffer[Student]]
  
  var bufferedSource = io.Source.fromFile("univ.csv")
    for (line <- bufferedSource.getLines) {
        val cols = line.split(",").map(_.trim)
        // do whatever you want with the columns here
        println(s"${cols(0)}|${cols(1)}|${cols(2)}")
        addUniversityToMap({cols(0)},{cols(1)},{cols(2)})
    }
    bufferedSource.close
    println("==================================")
    for((k,v) <- universityMap) println("Key is "+k+ " and value is "+v.city)
    println("==================================")
    bufferedSource = io.Source.fromFile("students.csv")
    for (line <- bufferedSource.getLines) {
        val cols = line.split(",").map(_.trim)
        // do whatever you want with the columns here
        println(s"${cols(0)}|${cols(1)}|${cols(2)}|${cols(3)}")
        addStudentToUniversity({cols(0)},{cols(1).toInt},{cols(2)},{cols(3).toInt},{cols(4)},{cols(5)})
    }
    
    println("============== universityToStudentsMap ====================")
    for((k,v) <- universityToStudentsMap) println("v(0) is "+v(0).hobbies+" v(0) name is "+v(0).name)
    println("==================================")
    
    // Find universities in the city
//    var filterMap = universityMap.filter({case(x,y) => y.city=="California"})
    //Test 1
    findUniversitiesInCity("Alabama")
    
    
    // Test 2
    findStudentsInCourse("CSE","Alabama")
    
    // Test 3
    findPlayersWhoPlayFootballFromTwoUnivs("playing_soccer","Alabama","California")
    
    // Test 4
    findPlayersWhoPlayFootball("playing_soccer")
    
    // Test 5
    findUniversitiesWithScoreAndCourse(78,"ECE")
    
    
    def findUniversitiesWithScoreAndCourse(score:Int, course:String){
      println("Test 5 results")
      var students = ListBuffer.empty[String]
      universityToStudentsMap.values.foreach(studentsList => studentsList.foreach(student => if(student.score > score && student.course==course) students+=student.code))
      val univs = students.distinct
      for(univ <- univs) println( "university names are " +universityMap(univ).name)
    }
    
    
    
    def findPlayersWhoPlayFootball(hobbie:String){
      var students = ListBuffer.empty[Student]
      universityToStudentsMap.values.foreach(studentsList => studentsList.foreach(student => if(student.hobbies.contains(hobbie)) students+=student))
      var studFromFor = for(stud <- universityToStudentsMap.values.flatten if(stud.hobbies.contains(hobbie))) yield stud  
      for(stud<- students) println("Student name is "+stud.name +" college code is "+stud.code+" Hobbies are "+stud.hobbies)
      println("=================================== Duplicated Output ===============================================================")
      for(stud<- studFromFor) println("Student name is "+stud.name +" college code is "+stud.code+" Hobbies are "+stud.hobbies)
      println("=================================== Duplicated Output ===============================================================")
    }
    
    
    def findPlayersWhoPlayFootballFromTwoUnivs(hobbie: String, univ1:String, univ2:String){
      val codes = for((k,v) <- universityMap if(v.name.contains(univ1)|| v.name.contains(univ2))) yield k
      println("codes are "+ codes)
      var students = ListBuffer.empty[Student]
      for(key <- universityToStudentsMap.keys){
        if(codes.toList.contains(key)){
          universityToStudentsMap.get(key).foreach(studentsList => studentsList.foreach(stud => if(stud.hobbies.contains(hobbie)) students+=stud))
        }
      }
      for(stud<- students) println("Name is "+stud.name+" and code is "+stud.code)
      // Duplicated.............................
      
//     universityToStudentsMap.values.flatten.foreach(student => if(codes.toList.contains(student.code) && student.hobbies.contains(hobbie)))
     var studentsFromTwoUnivs = for(student <- universityToStudentsMap.values.flatten if(codes.toList.contains(student.code) && student.hobbies.contains(hobbie))) yield student
     println("=================================== Duplicated Output ===============================================================")
     for(stud<- studentsFromTwoUnivs) println("Name is "+stud.name+" and code is "+stud.code)
     println("=================================== Duplicated Output ===============================================================")
      
    }
    
    
    def findStudentsInCourse(course:String, name:String){
      val codes = for((k,v) <- universityMap if(v.name.contains(name))) yield k
      println("codes are "+ codes)
      var students = ListBuffer.empty[Student]
      for(key <- universityToStudentsMap.keys){
        if(codes.toList.contains(key)){
          universityToStudentsMap.get(key).foreach(studentsList => studentsList.foreach(stud => if(stud.course==course) students+=stud))
        }
      }
      
      for(stud<- students) println("Name is "+stud.name+" and id is "+stud.rollNo)
      
      // Duplicated........................
      
      var studentsMap = for(student <- universityToStudentsMap.values.flatten if(universityMap(student.code).name.contains(name) && student.course==course)) yield student
      println("=================================== Duplicated Output using flatten ===============================================================")
      for(stud<- studentsMap) println("Name is "+stud.name+" and id is "+stud.rollNo)
      println("=================================== Duplicated Output using flatten ===============================================================")
      
      println("=================================== Duplicated Output using filter ===============================================================")
      universityToStudentsMap.values.flatten.filter(student => universityMap(student.code).name.contains(name) && student.course==course).foreach(p=> println("Name is "+p.name+" id is "+p.rollNo))
      println("=================================== Duplicated Output using filter ===============================================================")
            
    }
    
  
    def findUniversitiesInCity(name:String){
          var filterMap = for((k,v) <- universityMap if(v.city.equals(name))) yield (k,v)
          println("Finding universities in "+name)
          for((k,v) <- filterMap) println("Key is "+k+" value is "+v.city)
    }
  
    def addUniversityToMap(code: String, name: String, city:String){
      universityMap += (code -> new University(name,code,city))
    }
    
    def addStudentToUniversity(name:String,rollNo:Int,course:String,score:Int,code:String,hobbies:String){
      println("Adding student to University")
     var student = new Student(name,rollNo, course,score,code,hobbies.split(" ").toList)
     universityToStudentsMap.getOrElseUpdate(code, ListBuffer.empty[Student]) += student
      /*if(universityToStudentsMap.contains(code)){
        universityToStudentsMap(code)+=student
      }else{
        var studentList = ListBuffer.empty[Student]
        studentList+=student
        universityToStudentsMap+=(code -> studentList)
      }*/
    }
  
}