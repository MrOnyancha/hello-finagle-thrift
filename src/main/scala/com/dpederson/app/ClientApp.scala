package com.dpederson
package app

import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.thrift.ThriftClientFramedCodec
import hello._
import org.apache.thrift.protocol.TBinaryProtocol

/**
 * Client app - uses ZooKeeper to find and call the hello service.
 */
object ClientApp extends App {

  val helloService = ClientBuilder().codec(ThriftClientFramedCodec())
    .cluster(ZooKeeperHelper.cluster("/helloService")).hostConnectionLimit(1).build()
  val helloClient = new HelloService$FinagleClient(helloService, new TBinaryProtocol.Factory())
  helloClient.ping()
  helloClient.sayHello(HelloMsg("from Scala")) onSuccess { result =>
    println(s"Client received response message: ${result.name}")
  } ensure {
    helloService.close()
  }

} // ClientApp
