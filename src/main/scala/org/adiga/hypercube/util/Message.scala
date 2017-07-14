package org.adiga.hypercube.util

import scala.util.parsing.json.JSONObject

/**
  * Created by adiga on 26/3/17.
  */
object Message {

  case class WorkerRegistration()
  case class CloseAndDie(msg: String)

  case class Message(msg: JSONObject)
  case class WorkerId(id: String)
  case class Task(id: String, task: JSONObject, workerId: WorkerId)
}
