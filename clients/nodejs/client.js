var thrift = require('thrift')
  , ttypes = require('./gen-nodejs/hello_types')
  , HelloService = require('./gen-nodejs/HelloService.js')
  , host = 'localhost'
  , port = 5555;

exports.sayHello = function(name, callback) {
  var connection = thrift.createConnection(host, port, {transport: thrift.TFramedTransport});
  var client = thrift.createClient(HelloService, connection);
  connection.on('error', function(err) {
    return callback(err);
  });
  client.ping(function(_err) {
    if (_err) {
      return callback(_err);
    } else {
      var msg = new ttypes.HelloMsg({ name: name });
      client.sayHello(msg, function(err2, response) {
      if (err2) {
          return callback(err2);
      } else {
          return callback(null, response);
      }
      connection.end();
      });
    }
  });
}
