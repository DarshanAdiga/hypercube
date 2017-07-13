package org.adiga.hypercube.worker

import akka.actor.{ActorSystem, _}
import akka.cluster.ClusterEvent.{CurrentClusterState, MemberUp}
import akka.cluster.{Cluster, Member, MemberStatus}
import com.typesafe.config.ConfigFactory
import org.adiga.hypercube.util.C
import org.adiga.hypercube.util.Message.{CloseAndDie, WorkerRegistration}

/**
 * Worker actor which does the labour
 */
class WorkerActor extends Actor {
  val cluster = Cluster(context.system)
  var treeLoaded = false

  /*Subscribe to cluster state events*/
  override def preStart(): Unit = {
    cluster.subscribe(self, classOf[MemberUp])
  }
  /*Un-subscribe from cluster state events*/
  override def postStop(): Unit = {
    cluster.unsubscribe(self)
  }

  override def receive: Receive = {
    case msg: String => {
      println("Worker received " + msg + " from " + sender)
      sender ! "hi"
    }

    /*If a new member came up, register yourself with him*/
    case state: CurrentClusterState => {
      println("Worker received cluster state event")
      state.members.filter(_.status == MemberStatus.Up) foreach register
    }
    case MemberUp(member) => {
      println("Worker received member up event")
      register(member)
    }

    case CloseAndDie(msg: String) => {
      die(msg)
    }
    case m => println("Worker received unknown msg " + m)
  }

  /*Register with the member if he is a master*/
  def register(member: Member): Unit = {
    if (member.hasRole(C.Master.ROLE)) {
      val masterActor = context.actorSelection(RootActorPath(member.address) / "user" / C.Master.ROLE)
      masterActor ! WorkerRegistration
    }
  }

  def die(msg: String): Unit = {
    println("Master asked to close and die!")
    println("Reason:" + msg)
    context.system.stop(context.self)
    context.system.terminate()
    System.exit(0)
  }
}

object WorkerActor {
  val usage = """
    Usage: java -cp distributed-hash-lookup.jar <port>
  """

  def main(args: Array[String]) {
    if (args.length == 0) {
      println(usage)
      System.exit(1)
    }
    /*Get the port from arguments*/
    val port = if (args.isEmpty) "0" else args(0)
    /*Load the config*/
    val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$port")
      .withFallback(ConfigFactory.parseString(C.Worker.CLUSTER_ROLE))
      .withFallback(ConfigFactory.load())

    /*Initialize the actor system*/
    val workerSystem = ActorSystem(C.ACTOR_SYSTEM, config)
    val workerActor = workerSystem.actorOf(Props[WorkerActor], name = C.Worker.ROLE)
    println("Worker is ready at " + workerActor.toString())
  }
}


