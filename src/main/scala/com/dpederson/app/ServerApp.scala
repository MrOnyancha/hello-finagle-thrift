package com.dpederson
package app

import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.thrift.ThriftServerFramedCodec
import hello._
import org.apache.thrift.protocol.TBinaryProtocol

/**
 * Start the hello service server on port 8001.
 */
object ServerApp extends App {

  val protocol = new TBinaryProtocol.Factory()
  val service = new HelloService$FinagleService(HelloServiceProcessor(), protocol)
  val serverHost = new java.net.InetSocketAddress(8001)
  ZooKeeperHelper.cluster("/helloService").join(serverHost)
  ServerBuilder().bindTo(serverHost).codec(ThriftServerFramedCodec())
    .name("helloService").build(service)

} // ServerApp
