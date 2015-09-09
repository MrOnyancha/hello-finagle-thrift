package zdavep.hello

import com.twitter.finagle.ThriftMux
import com.twitter.util.Await
import org.slf4j.LoggerFactory

/**
 * Start the hello service server on a given port.
 */
object HelloServiceServer extends App {

  val logger = LoggerFactory.getLogger(getClass)

  val defPort = 5555
  val path = "/zdavep/hello/v1"
  val port = if (args.length > 0) args(0).toInt else defPort
  val serverHost = new java.net.InetSocketAddress(port)

  logger.info("Starting HelloService server...")
  val server = ThriftMux.server.serveIface(serverHost, HelloServiceImpl())

  logger.info("Joining ZooKeeper cluster...")
  val _ = ZooKeeperHelper.join(path, serverHost)

  logger.info(s"Server ready!")
  Await.ready(server)
}
