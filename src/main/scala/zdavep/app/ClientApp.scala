package zdavep
package app

import com.twitter.finagle.Thrift
import com.twitter.util.Await
import hello._
import org.slf4j.LoggerFactory

/**
 * Client app - uses ZooKeeper to find and call the hello service.
 */
object ClientApp extends App {

  val logger = LoggerFactory.getLogger(getClass)

  val name = if (args.length > 0) args(0) else "from Scala"
  val helloClient = Thrift.newIface[HelloService.FutureIface]("zk!localhost:2181!/zdavep/hello/v1")
  val callServices = helloClient.ping() flatMap { _ =>
    helloClient.sayHello(HelloMsg(name))
  }

  callServices onSuccess { result =>
    logger.info(s"Client received message: ${result.name}\n")
  } onFailure { ex =>
    logger.error("Error calling service", ex)
  }

  val _ = Await.result(callServices) // Don't do this in PROD
}
