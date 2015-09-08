var clientFactory = require('./client')
  , client = new clientFactory.HelloClient({host:'localhost', port:5555});

client.ping(function(e, ok) {
  console.log(e != null ? e : 'ping: ' + ok);
  client.close();
  process.exit(0);
});
