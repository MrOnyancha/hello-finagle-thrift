package zdavep
package hello

import collection.JavaConverters._
import com.twitter.common.quantity.{Amount, Time}
import com.twitter.common.zookeeper.{ServerSet, ServerSetImpl, ZooKeeperClient}
import java.net.InetSocketAddress

/**
 * ZooKeeper helper
 */
object ZooKeeperHelper {

  // Constants
  private val timeout = 10
  private val zkHost = "localhost"
  private val zkPort = 2181
  private val zooKeeperHost = new InetSocketAddress(zkHost, zkPort)

  // Create server set
  private def serverSet(name: String): ServerSet = new ServerSetImpl(
    new ZooKeeperClient(Amount.of(timeout, Time.SECONDS), zooKeeperHost), name)

  /**
   * Register a serivce with ZooKeeper.
   */
  def join(name: String, host: InetSocketAddress): ServerSet.EndpointStatus =
    serverSet(name).join(host, Map.empty[String, InetSocketAddress].asJava, 0)
}
