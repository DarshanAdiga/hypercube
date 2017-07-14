package org.adiga.hypercube.worker

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import org.adiga.hypercube.util.C

/**
  * Created by adiga on 15/7/17.
  */
class BaseWorker(port: Int) {
  /*Load the config*/
  val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$port")
    .withFallback(ConfigFactory.parseString(C.Worker.CLUSTER_ROLE))
    .withFallback(ConfigFactory.load())

  /*Initialize the actor system*/
  val workerSystem = ActorSystem(C.ACTOR_SYSTEM, config)
  val workerActor = workerSystem.actorOf(Props[WorkerActor], name = C.Worker.ROLE)
  println("Worker is ready at " + workerActor.toString())
}

object BaseWorker extends App {
  val usage = """
               Usage: java -cp hypercube-worker.jar <port>
              """
  if (args.length == 0) {
    println(usage)
    System.exit(1)
  }
  /*Get the port from arguments*/
  val port = if (args.isEmpty) "0" else args(0)
  new BaseWorker(port.toInt)
}
