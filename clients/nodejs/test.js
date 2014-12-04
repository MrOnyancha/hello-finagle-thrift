var clientFactory = require('./client');

var name = 'from nodejs';

var client = new clientFactory.HelloClient({host:'localhost', port:5555});

client.sayHello(name, function(err, resp) {
  console.log(err != null ? err : resp);
  client.close();
  process.exit(0);
});
