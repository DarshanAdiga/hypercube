package org.adiga.hypercube.worker

import org.adiga.hypercube.common.MessageListener
import org.adiga.hypercube.util.Message.{Message, Task}

/**
  * Interface used by user to define the workers
  * Created by adiga on 15/7/17.
  */
abstract class AbstractWorker(port: Int) extends MessageListener {
  val baseWorker = new BaseWorker(port)
  def init() = {
    println("Worker has been initialized")
  }

  /**
    * Interface for which user has to provide an implementation
    * to handle the assigned task
    * @param task
    */
  def onTaskAssigned(task: Task)

  /**
    * Interface used by the worker to communicate with the master
    * @param msg
    */
  def sendToMaster(msg: Message) = {
    //TODO
  }
}
