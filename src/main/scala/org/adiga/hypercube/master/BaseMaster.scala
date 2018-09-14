package org.adiga.hypercube.master

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import org.adiga.hypercube.util.C
import org.adiga.hypercube.util.C.Master

/**
  * Created by adiga on 15/7/17.
  */
class BaseMaster {
  /*Load the config*/
  val config = ConfigFactory.parseString(Master.CLUSTER_ROLE)
    .withFallback(ConfigFactory.load())

  /*Initialize the actor system*/
  val masterSystem = ActorSystem(C.ACTOR_SYSTEM, config)
  val masterActor = masterSystem.actorOf(Props[MasterActor], name = C.Master.ROLE)
  println("Master is ready at " + masterActor.toString())
}

object BaseMaster extends App {
  new BaseMaster()
}
