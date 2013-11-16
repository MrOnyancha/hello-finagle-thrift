package com.dpederson
package hello

import com.twitter.util.Future

class HelloServiceImpl extends HelloService[Future] {

  override def sayHello(msg: HelloMsg): Future[HelloMsg] =
    Future.value(HelloMsg("Hello, %s!" format msg.name))

} // HelloServiceImpl

