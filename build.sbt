ThisBuild / scalaVersion := "2.13.8"

ThisBuild / version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """bbs_backend""",
    libraryDependencies ++= Seq(
      guice,
      jdbc,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
      "mysql" % "mysql-connector-java" % "8.0.29",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
      "org.skinny-framework" %% "skinny-orm"      % "3.0.3",
      "ch.qos.logback"       %  "logback-classic" % "1.2.11",
      "org.scalikejdbc" %% "scalikejdbc"                  % "3.5.0",
      "org.scalikejdbc" %% "scalikejdbc-config"           % "3.5.0",
      "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.8.0-scalikejdbc-3.5",
      "org.scalikejdbc" %% "scalikejdbc-play-dbapi-adapter" % "2.8.0-scalikejdbc-3.5",
      "io.jsonwebtoken" % "jjwt" % "0.9.1",
      "com.github.t3hnar" %% "scala-bcrypt" % "4.3.0",
    )
  )