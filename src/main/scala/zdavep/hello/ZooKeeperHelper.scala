package zdavep.hello

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
  private val empty = Map.empty[String, InetSocketAddress].asJava
  private val shard = 0

  // Create server set
  private def serverSet(path: String): ServerSet = new ServerSetImpl(
    new ZooKeeperClient(Amount.of(timeout, Time.SECONDS), zooKeeperHost), path)

  /**
   * Register a serivce with ZooKeeper.
   */
  def join(path: String, host: InetSocketAddress): ServerSet.EndpointStatus =
    serverSet(path).join(host, empty, shard)
}
