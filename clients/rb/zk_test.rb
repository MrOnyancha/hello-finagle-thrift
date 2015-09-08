#!/usr/bin/env ruby

require 'rubygems'
require 'zookeeper'
require 'json'

z = Zookeeper.new("localhost:2181")
path = z.get_children(:path => "/zdavep/hello/v1")
path[:children].each { |child|
  node = z.get(:path => "/zdavep/hello/v1/#{child}")
  data = JSON.parse(node[:data])
  data['serviceEndpoint'].each { |k,v|
    puts "#{k} = #{v}" unless k.nil? or v.nil?
  }
}
