package zdavep.hello

import com.twitter.finagle.tracing.Trace
import com.twitter.util.Future
import org.slf4j.LoggerFactory

/**
 * The HelloService implementation.
 */
object HelloServiceImpl {

  val logger = LoggerFactory.getLogger(getClass)

  /**
   * HelloService implementation.
   */
  def apply(): HelloService[Future] = new HelloService[Future] {

    /**
     * The sayHello service function implementation
     */
    override def sayHello(msg: HelloMsg): Future[HelloMsg] = Future.value {
      Trace.time("sayHello") {
        val sleepMs = 100
        Thread.sleep(sleepMs)
        logger.info(s"Server received message: ${msg.name}")
        HelloMsg(s"Hello, ${msg.name}")
      }
    }

    /**
     * The ping service function implementation
     */
    override def ping(): Future[Unit] = {
      Trace.record("ping called")
      logger.info("ping called")
      Future.Unit
    }

    override def zip(): Future[Unit] = {
      Trace.record("zip called")
      logger.info("zip called")
      Future.Unit
    }
  }
}
