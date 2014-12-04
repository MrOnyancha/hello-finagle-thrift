var client = require('./client');

var name = 'from nodejs';

client.sayHello(name, function(err, resp) {
  if (err) {
    console.log(err);
    process.exit(1);
  } else {
    if (resp) {
      console.log(resp.name);
    } else {
      console.log('nil response');
    }
    process.exit(0);
  }
});
