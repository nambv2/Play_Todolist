name := "Play_TodoList"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.mongodb" % "mongo-java-driver" % "2.11.3",
  "com.google.code.gson" % "gson" % "2.2.4"
)     

play.Project.playJavaSettings
