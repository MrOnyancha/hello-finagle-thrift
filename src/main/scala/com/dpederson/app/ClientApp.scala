package com.dpederson
package app

import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.thrift.ThriftClientFramedCodec
import hello._
import org.apache.thrift.protocol.TBinaryProtocol

object ClientApp extends App {

  val helloService = ClientBuilder().hosts("localhost:8001").codec(ThriftClientFramedCodec())
    .hostConnectionLimit(1).build()
  val helloClient = new HelloService$FinagleClient(helloService, new TBinaryProtocol.Factory())
  helloClient.sayHello(HelloMsg("from Scala")) onSuccess { result =>
    println(s"Client received response message: ${result.name}")
  } ensure {
    helloService.close()
  }

} // ClientApp
