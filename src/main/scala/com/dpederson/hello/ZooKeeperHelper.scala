package com.dpederson
package hello

import com.twitter.common.quantity.{ Amount, Time }
import com.twitter.common.zookeeper.{ ServerSets, ServerSetImpl, ZooKeeperClient }
import com.twitter.finagle.zookeeper.ZookeeperServerSetCluster
import java.net.InetSocketAddress

/**
 * ZooKeeper helper functions
 */
object ZooKeeperHelper {

  /**
   * Zookeeper client configuration helper.
   */
  def cluster(serviceName: String): ZookeeperServerSetCluster = {
    val sessionTimeout = Amount.of(10, Time.SECONDS)
    val zookeeperClient = new ZooKeeperClient(sessionTimeout, new InetSocketAddress(2181))
    val serverSet = new ServerSetImpl(zookeeperClient, serviceName)
    new ZookeeperServerSetCluster(serverSet)
  }

} // ZooKeeperHelper
