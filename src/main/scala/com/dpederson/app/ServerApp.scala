package com.dpederson
package app

import com.twitter.finagle.thrift.ThriftServerFramedCodec
import org.apache.thrift.protocol.TBinaryProtocol
import com.twitter.finagle.builder.{ServerBuilder, Server}
import java.net.InetSocketAddress
import com.twitter.util.Future

import hello._

object ServerApp extends App {

   /*val helloServer = new HelloServiceImpl with HelloService.ThriftServer {
    override val thriftPort = 8001
    override val serverName = "helloserver"
  }
  helloServer.start()*/

  val processor = new HelloServiceImpl
  val service = new HelloService.FinagledService(processor, new TBinaryProtocol.Factory())
  val server: Server = ServerBuilder()
    .bindTo(new InetSocketAddress("0.0.0.0", 8001))
    .codec(ThriftServerFramedCodec())
    .name("helloserver")
    .build(service)

} // ServerApp
