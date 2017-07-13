package org.adiga.hypercube.util

import java.io.File

import akka.actor.ActorSystem
import akka.pattern.ask
import scala.concurrent.duration._
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import Message.{CloseAndDie, LoadDictionary}

import scala.concurrent.Await

/**
  * Created by adiga on 2/4/17.
  */
object ActorTests {

  def main(args: Array[String]): Unit = {
    val config = ConfigFactory
      .parseFile(new File("src/main/resources/test-app.conf"))

    val testSystem = ActorSystem("Test", config)

    implicit val timeout = Timeout(5 seconds)
    val masterSelection = testSystem.actorSelection("akka.tcp://Hypercube@127.0.0.1:2210/user/master")
    val masterFuture = masterSelection.resolveOne(timeout.duration)
    val masterActor = Await.result(masterFuture, timeout.duration)
    masterActor ! "Hello there"

    Thread.sleep(2 * 1000)
    val resp = masterActor ? CloseAndDie("Good bye guys!")
    val msg = Await.result(resp, timeout.duration).asInstanceOf[String]
    println(s"Received message from the master: $msg")
    testSystem.terminate()
  }
}
