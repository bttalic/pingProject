name := "pingProject"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.21",
  "postgresql" % "postgresql" % "8.4-702.jdbc4"
)     

play.Project.playJavaSettings
