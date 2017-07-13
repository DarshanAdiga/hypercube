package org.adiga.hypercube.util

import java.io.File

/**
  * Created by adiga on 26/3/17.
  */
object Message {

  case class LoadDictionaries()
  case class WorkerRegistration()
  case class LoadDictionary(file: File) {
    def this(filePath: String) = this(new File(filePath))
  }
  case class LoadStatus(file: File, status: Boolean) {
    def this(filePath: String, status: Boolean) = this(new File(filePath), status)
  }
  case class CloseAndDie(msg: String)
}
