package org.adiga.hypercube.common

import org.adiga.hypercube.util.Message.Message

/**
  * Interface used by entities to communicate
  * Created by adiga on 15/7/17.
  */
trait MessageListener {
  def onMessage(msg: Message)
}
