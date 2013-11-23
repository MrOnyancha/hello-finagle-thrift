package com.dpederson
package app

import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.thrift.ThriftServerFramedCodec
import hello._

/**
 * Start the hello service server on a given port.
 */
object ServerApp extends App {

  val protocol = new org.apache.thrift.protocol.TBinaryProtocol.Factory()
  val service = new HelloService$FinagleService(HelloServiceProcessor(), protocol)
  val port = if (args.length > 0) args(0).toInt else 8001
  val serverHost = new java.net.InetSocketAddress(port)
  ZooKeeperHelper.cluster("/helloService").join(serverHost)
  ServerBuilder().bindTo(serverHost).codec(ThriftServerFramedCodec())
    .name("helloService").build(service)

} // ServerApp
