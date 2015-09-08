#!/usr/bin/env ruby

$:.push('gen-rb')

require 'rubygems'
require 'hello_service'
require 'zookeeper'
require 'json'

host, port = 'localhost', 8001
zk = Zookeeper.new("localhost:2181")
path = zk.get_children(:path => "/zdavep/hello/v1")
path[:children].each { |child|
  node = zk.get(:path => "/zdavep/hello/v1/#{child}")
  data = JSON.parse(node[:data])
  host = data['serviceEndpoint']['host']
  port = data['serviceEndpoint']['port'].to_i
}
zk.close()

begin
  socket = Thrift::Socket.new(host, port)
  transport = Thrift::FramedTransport.new(socket)
  protocol = Thrift::BinaryProtocol.new(transport)
  client = HelloService::Client.new(protocol)
  transport.open()

  client.ping()

  msg = HelloMsg.new()
  msg.name = "from Ruby"
  reply = client.sayHello(msg)
  puts reply.name

  transport.close()

rescue Thrift::Exception => tx
  print 'Thrift::Exception: ', tx.message, "\n"
end
