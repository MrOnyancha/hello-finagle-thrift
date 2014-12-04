var thrift = require('thrift')
  , ttypes = require('./gen-nodejs/hello_types')
  , HelloService = require('./gen-nodejs/HelloService.js')
  , host = 'localhost'
  , port = 5555;

var connection = thrift.createConnection(host, port, {transport: thrift.TFramedTransport});

connection.on('error', function(err) {
  console.log(err);
});

exports.close = function() { connection.end(); }

exports.sayHello = function(name, callback) {
  var client = thrift.createClient(HelloService, connection);
  client.ping(function(ping_err) {
    if (ping_err) {
      return callback(ping_err);
    } else {
      client.sayHello(new ttypes.HelloMsg({name:name}), function(err, response) {
        return err != null ? callback(err) : callback(null, response);
      });
    }
  });
}
