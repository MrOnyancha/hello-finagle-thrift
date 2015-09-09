package zdavep.hello

import com.twitter.finagle.ThriftMux
import com.twitter.util.Await
import org.slf4j.LoggerFactory

/**
 * Client app - uses ZooKeeper to find and call the hello service.
 */
object HelloServiceClient extends App {

  val logger = LoggerFactory.getLogger(getClass)

  val name = if (args.length > 0) args(0) else "from Scala"
  val dest = "zk!localhost:2181!/zdavep/hello/v1"
  val helloClient = ThriftMux.client.newIface[HelloService.FutureIface](dest)
  val callServices = helloClient.ping() flatMap { _ =>
    helloClient.sayHello(HelloMsg(name))
  }

  callServices onSuccess { msg =>
    logger.info(s"Client received message: ${msg.name}\n")
  } onFailure { ex =>
    logger.error("Error calling service", ex)
  }

  val _ = Await.ready(callServices)
}
