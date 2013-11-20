package com.dpederson
package hello

import com.twitter.util.Future
import org.slf4j.{ Logger, LoggerFactory }

/**
 * The HelloService implementation.
 */
object HelloServiceProcessor {

  /**
   * Logger
   */
  val logger: Logger = LoggerFactory.getLogger("HelloServiceProcessor")

  /**
   * HelloService implementation.
   */
  def apply(): HelloService[Future] = new HelloService[Future] {
    override def sayHello(msg: HelloMsg): Future[HelloMsg] = {
      logger.info(s"Server received message: ${msg.name}")
      Future.value(HelloMsg(s"Hello, ${msg.name}"))
    }
    override def ping(): Future[Unit] = {
      logger.info("ping called")
      Future.value()
    }
  }

} // HelloServiceProcessor
