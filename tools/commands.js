var http = require("http");
var https = require("https");
var fs = require('fs');

var Handlebars = require('handlebars');
require('handlebars-helpers')();

/**
 * getJSON:  REST get request returning JSON object(s)
 * @param options: http options object
 * @param callback: callback to pass the results JSON object(s) back
 */
function getJSON (options, callback)
{
  var port = options.port === 443 ? https : http;
  var req = port.request(options, function(res)
  {
    var output = '';
    res.setEncoding('utf8');

    res.on('data', function (chunk) {
      output += chunk;
    });

    res.on('end', function() {
      var obj = JSON.parse(output);
      callback(res.statusCode, obj);
    });
  });

  req.on('error', console.error);

  req.end();
}

var cmd_template = Handlebars.compile(fs.readFileSync('redis-command.hbs', 'utf8'));

var options = {
  host: 'redis.io',
  port: 443,
  path: '/commands.json',
  method: 'GET',
  headers: {
    'Content-Type': 'application/json'
  }
};

getJSON(options, function(statusCode, result) {
  fs.writeFileSync('../src/main/java/io/vertx/redis/RedisCommandEnum.java', cmd_template(result));
});
