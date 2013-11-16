package com.dpederson
package app

import com.twitter.finagle.builder.{ServerBuilder, Server}
import com.twitter.finagle.thrift.ThriftServerFramedCodec
import com.twitter.util.Future
import hello._
import java.net.InetSocketAddress
import org.apache.thrift.protocol.TBinaryProtocol

object ServerApp extends App {

  val processor = new HelloServiceImpl
  val service = new HelloService$FinagleService(processor, new TBinaryProtocol.Factory())
  val server: Server = ServerBuilder()
    .bindTo(new InetSocketAddress("0.0.0.0", 8001))
    .codec(ThriftServerFramedCodec())
    .name("helloserver")
    .build(service)

} // ServerApp
