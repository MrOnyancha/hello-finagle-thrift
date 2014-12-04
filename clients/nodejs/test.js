var client = require('./client');

var name = 'from nodejs';

client.sayHello(name, function(err, resp) {
  console.log(err != null ? err : resp);
  client.close();
  process.exit(0);
});
