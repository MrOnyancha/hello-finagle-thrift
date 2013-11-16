package com.dpederson
package hello

import com.twitter.util.Future

object HelloServiceProcessor {
  def apply(): HelloService[Future] = new HelloService[Future] {
    override def sayHello(msg: HelloMsg): Future[HelloMsg] = Future.value(
      HelloMsg("Hello, %s!" format msg.name)
    )
  }
}
