package zdavep
package hello

import collection.JavaConverters._
import com.twitter.common.quantity.{ Amount, Time }
import com.twitter.common.zookeeper.{ ServerSet, ServerSetImpl, ZooKeeperClient }
import java.net.InetSocketAddress

/**
 * ZooKeeper helper functions
 */
object ZooKeeperHelper {

  /**
   * Zookeeper client configuration helper.
   */
  private[this]
  def cluster(serviceName: String): ServerSet = {
    val sessionTimeout = Amount.of(10, Time.SECONDS)
    val zooKeeperHost = new InetSocketAddress("localhost", 2181)
    val zooKeeperClient = new ZooKeeperClient(sessionTimeout, zooKeeperHost)
    new ServerSetImpl(zooKeeperClient, serviceName)
  }

  /**
   * Join a ZooKeeper cluster.
   */
  def join(serviceName: String, serverHost: InetSocketAddress): Unit = {
    cluster(serviceName).join(serverHost, Map.empty[String, InetSocketAddress].asJava, 0)
  }

} // ZooKeeperHelper
