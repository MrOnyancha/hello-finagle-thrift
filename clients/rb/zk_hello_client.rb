#!/usr/bin/env ruby

$:.push('gen-rb')

require 'rubygems'
require 'hello_service'
require 'zookeeper'
require 'json'

host, port = 'localhost', 8001
z = Zookeeper.new("localhost:2181")
path = z.get_children(:path => "/helloService")
path[:children].each { |child|
  node = z.get(:path => "/helloService/#{child}")
  data = JSON.parse(node[:data])
  #host = data['serviceEndpoint']['host']
  port = data['serviceEndpoint']['port'].to_i
}
z.close()

begin
  socket = Thrift::Socket.new(host, port)
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
