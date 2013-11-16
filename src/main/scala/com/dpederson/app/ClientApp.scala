package com.dpederson
package app

import com.twitter.conversions.time._
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.thrift.{ThriftClientFramedCodec,ThriftClientRequest}
import com.twitter.util.Future

import hello._

object ClientApp extends App {

  val helloService = ClientBuilder()
    .hosts("localhost:8001")
    .codec(ThriftClientFramedCodec())
    .hostConnectionLimit(1)
    .build()
    
  val helloClient = new HelloService.FinagledClient(helloService)
  
  val response: Future[HelloMsg] = helloClient.sayHello(HelloMsg("from Scala"))
  
  val result  = response(3 seconds)
  
  println(result.name)
  
  helloService.release()
}
