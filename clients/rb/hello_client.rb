#!/usr/bin/env ruby

$:.push('gen-rb')

require 'rubygems'
require 'hello_service'

begin
  socket = Thrift::Socket.new('localhost', 8001)
  transport = Thrift::FramedTransport.new(socket)
  protocol = Thrift::BinaryProtocol.new(transport)
  client = HelloService::Client.new(protocol)
  transport.open()

  client.ping()

  msg = HelloMsg.new()
  msg.name = "from Ruby"
  msg = client.sayHello(msg)
  puts msg.name

  transport.close()

rescue Thrift::Exception => tx
  print 'Thrift::Exception: ', tx.message, "\n"
end
