package org.adiga.hypercube.master

import org.adiga.hypercube.common.MessageListener
import org.adiga.hypercube.util.Message.{Message, Task, WorkerId}

/**
  * Interface used by user to define his master
  * Created by adiga on 15/7/17.
  */
abstract class AbstractMaster extends MessageListener {
  val baseMaster = new BaseMaster()
  def init() = {
    println("Master has been initialized")
  }
  def spawnWorker(workerId: WorkerId) = {
    //TODO
  }
  def assignTask(task: Task) = {
    //TODO
  }

  /**
    * Interface used by the master to communicate with the worker
    * @param msg
    * @param workerId
    */
  def sendToWorker(msg: Message, workerId: WorkerId) = {
    //TODO
  }
}
