var thrift = require('thrift')
  , ttypes = require('./gen-nodejs/hello_types')
  , HelloService = require('./gen-nodejs/HelloService.js');

HelloClient = module.exports.HelloClient = function(args) {
  this.host = null;
  this.port = null;
  if (args) {
    if (args.host !== undefined) {
      this.host = args.host;
    } else {
      throw 'Required field host is unset!';
    }
    if (args.port !== undefined) {
      this.port = args.port;
    } else {
      throw 'Required field port is unset!';
    }
  }
  this.connection = thrift.createConnection(this.host, this.port, {transport: thrift.TFramedTransport});
  this.connection.on('error', function(err) {
    throw err;
  });
};

HelloClient.prototype = {};
HelloClient.prototype.close = function() {
  this.connection.end(); 
};

HelloClient.prototype.ping = function(callback) {
  var client = thrift.createClient(HelloService, this.connection);
  client.ping(function(ping_err) {
    if (ping_err) {
      return callback(ping_err);
    } else {
      return callback(null, 'ok');
    }
  });
};

HelloClient.prototype.sayHello = function(name, callback) {
  var client = thrift.createClient(HelloService, this.connection);
  client.sayHello(new ttypes.HelloMsg({name:name}), function(err, response) {
    return err != null ? callback(err) : callback(null, response);
  });
};
