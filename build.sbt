name := "Battleship_Scala"

version := "0.2"

scalaVersion := "2.12.6"

scalacOptions ++= Seq(
    "-deprecation"
)

lazy val battleship = (project in file("."))
    .settings(
        name := "Battleship_Godefroi_Roussel",
        libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test,
    )
