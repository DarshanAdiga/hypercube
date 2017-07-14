package org.adiga.hypercube.util

/**
  * Created by adiga on 26/3/17.
  */
object Message {

  case class WorkerRegistration()
  case class CloseAndDie(msg: String)
}
