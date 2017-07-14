package org.adiga.hypercube.master

import akka.actor.{Actor, ActorRef, Terminated}
import org.adiga.hypercube.util.Message.{CloseAndDie, WorkerRegistration}

/**
 * Master actor which communicates with the workers
 */
class MasterActor() extends Actor {

  /*List of active workers available*/
  var activeWorkers = IndexedSeq.empty[ActorRef]
  var treeCounter = 0

  override def receive: Receive = {
    case msg: String => {
      println("Received a text message from " + sender + ":" + msg)
    }

    case CloseAndDie(msg: String) => {
      println(s"Client is asking to close and wrap everything up! In his own words: $msg")
      for (worker <- activeWorkers) worker ! CloseAndDie(msg)
      sender ! "Closed"
      die(msg)
    }
    /*A worker is registered and he is not in the active worker list*/
    case WorkerRegistration if (!activeWorkers.contains(sender)) => {
      context watch sender
      activeWorkers = activeWorkers :+ sender
      println("###Registered a new worker. Now worker count " + activeWorkers.size)
    }
    /*A worker is removed, so remove him from the list as well*/
    case Terminated(a) => {
      activeWorkers = activeWorkers.filterNot(_ == a)
      println("###Removed a worker. Now worker count " + activeWorkers.size)
      //TODO here find which dict file was loaded by that actor and add it to pending list
    }

    case m => println("Master received unknown msg " + m)
  }

  def die(reason: String): Unit = {
    println("Reason:" + reason)
    context.system.stop(context.self)
    context.system.terminate()
    System.exit(1)
  }
}
