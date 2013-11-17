package com.dpederson
package hello

import com.twitter.util.Future
import org.slf4j.{ Logger, LoggerFactory }

/**
 * The HelloService implementation.
 */
object HelloServiceProcessor {

  val logger: Logger = LoggerFactory.getLogger("HelloServiceProcessor")

  def apply(): HelloService[Future] = new HelloService[Future] {
    override def sayHello(msg: HelloMsg): Future[HelloMsg] = {
      logger.info(s"Received message: ${msg.name}")
      Future.value(HelloMsg("Hello, %s!" format msg.name))
    }
  }

} // HelloServiceProcessor
