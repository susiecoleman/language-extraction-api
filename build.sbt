name := "language-extraction-api"
      
lazy val languageExtractionApi = (project in file(".")).enablePlugins(PlayScala)
      
scalaVersion := "2.12.2"
val circeVersion = "0.11.1"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  ws)



      