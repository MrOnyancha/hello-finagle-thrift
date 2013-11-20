#!/usr/bin/env ruby

$:.push('gen-rb')

require 'rubygems'
require 'thrift'
require 'hello_service'
require 'hello_types'

begin
  socket = Thrift::Socket.new('localhost', 8001)
  transport = Thrift::FramedTransport.new(socket)
  protocol = Thrift::BinaryProtocol.new(transport)
  client = HelloService::Client.new(protocol)
  transport.open()

  msg = HelloMsg.new()
  msg.name = "from Ruby"
  msg = client.sayHello(msg)
  puts msg.name

  transport.close()

rescue Thrift::Exception => tx
  print 'Thrift::Exception: ', tx.message, "\n"
end
