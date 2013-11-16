#!/usr/bin/env python

import sys
sys.path.append('gen-py')

import hello
from hello import HelloService
from hello.ttypes import HelloMsg

from thrift.transport import TTransport
from thrift.transport import TSocket
from thrift.protocol import TBinaryProtocol

host = 'localhost'
port = 8001
text = 'from Python'
try:
    text = sys.argv[1]
except:
    pass

transport = TTransport.TFramedTransport(TSocket.TSocket(host, port))
client = HelloService.Client(TBinaryProtocol.TBinaryProtocol(transport))
transport.open()
try:
    msg = client.sayHello(HelloMsg(text))
    print(msg.name)
finally:
    transport.close()
