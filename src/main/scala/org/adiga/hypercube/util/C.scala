package org.adiga.hypercube.util

/**
  * Created by adiga on 2/4/17.
  */
object C {

  val ACTOR_SYSTEM = "AhoCorasick"
  object Master {
    val ROLE = "master"
    val CLUSTER_ROLE = "akka.cluster.roles = [" + ROLE + "])"
  }

  object Worker {
    val ROLE = "worker"
    val CLUSTER_ROLE = "akka.cluster.roles = [" + ROLE + "])"
  }
}
