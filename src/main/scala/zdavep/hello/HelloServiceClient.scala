package zdavep.hello

import com.twitter.conversions.time._
import com.twitter.util.{Await, Duration, Future}
import com.twitter.finagle._
import com.twitter.finagle.service.TimeoutFilter
import com.twitter.finagle.util.DefaultTimer
import org.slf4j.LoggerFactory

/**
 * Client app - Call the hello service using finagle services and filters.
 */
object HelloServiceClient extends App {

  val logger = LoggerFactory.getLogger(getClass)

  import HelloService._

  // Convert message name to uppercase before service call
  val uppercaseFilter = new SimpleFilter[SayHello.Args, SayHello.Result] {
    def apply(req: SayHello.Args, service: Service[SayHello.Args, SayHello.Result]):
        Future[SayHello.Result] = {
      val uppercaseRequest = req.copy(msg = HelloMsg(req.msg.name.toUpperCase))
      service(uppercaseRequest)
    }
  }

  // Adds a timeout to service calls
  def timeoutFilter[Req, Rep](duration: Duration): TimeoutFilter[Req, Rep] = {
    val exc = new IndividualRequestTimeoutException(duration)
    val timer = DefaultTimer.twitter
    new TimeoutFilter[Req, Rep](duration, exc, timer)
  }

  // Read CLI params and create service client
  val name = if (args.length > 0) args(0) else "from Scala"
  val dest = "zk!localhost:2181!/zdavep/hello/v1"
  val helloService = ThriftMux.client.newServiceIface[HelloService.ServiceIface](dest)

  // Create our sayHello service combinator
  val sayHello = timeoutFilter(500.millis) andThen uppercaseFilter andThen helloService.sayHello

  // Call services
  val callServices = helloService.ping(Ping.Args()).flatMap { _ =>
    sayHello(SayHello.Args(HelloMsg(name)))
  }
  callServices onSuccess { rep =>
    val name = rep.success.fold("") { msg => msg.name }
    logger.info(s"Got result: $name")
  }
  Await.result(callServices)
}
