package zdavep
package app

import com.twitter.finagle.ThriftMux
import com.twitter.util.Await
import org.slf4j.LoggerFactory
import hello._

/**
 * Start the hello service server on a given port.
 */
object ServerApp extends App {

  val logger = LoggerFactory.getLogger(getClass)

  val defPort = 5555
  val name = "/zdavep/hello/v1"
  val port = if (args.length > 0) args(0).toInt else defPort
  val serverHost = new java.net.InetSocketAddress(port)

  logger.info(s"Starting $name server...")
  val server = ThriftMux.server.serveIface(serverHost, HelloServiceProcessor())

  logger.info("Joining ZooKeeper cluster...")
  val _ = ZooKeeperHelper.join(name, serverHost)

  logger.info(s"Server ready!")
  Await.ready(server)
}
