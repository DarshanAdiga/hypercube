package org.adiga.hypercube.worker

import akka.actor._
import akka.cluster.ClusterEvent.{CurrentClusterState, MemberUp}
import akka.cluster.{Cluster, Member, MemberStatus}
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


