package com.dpederson
package app

import com.twitter.conversions.time._
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.thrift.{ThriftClientFramedCodec, ThriftClientRequest}
import com.twitter.util.Future
import hello._
import org.apache.thrift.protocol.TBinaryProtocol

object ClientApp extends App {

  val helloService = ClientBuilder()
    .hosts("localhost:8001")
    .codec(ThriftClientFramedCodec())
    .hostConnectionLimit(1)
    .build()

  val helloClient = new HelloService$FinagleClient(helloService, new TBinaryProtocol.Factory())
  helloClient.sayHello(HelloMsg("from Scala")) onSuccess { result =>
    println(result.name)
  } ensure {
    helloService.close()
  }

} // ClientApp
