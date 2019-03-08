var fs = require('fs');

var redis = require('redis').createClient(7006);
var Handlebars = require('handlebars');
require('handlebars-helpers')();

redis.command(function (err, res) {
  redis.quit();
  if (err) {
    return console.error(err);
  }

  // Ensure stable sorting of commands to avoid git diff churn every time we regenerate
  res.sort(function(a, b) {
    return a[0] < b[0] ? -1 : a[0] > b[0] ? 1 : 0;
  });

  var commands = [];

  res.forEach(function (cmd) {
    var types = "";
    var args = "";

    if (cmd[1] > 0) {
      for (let i = 0; i < cmd[1] - 1; i++) {
        if (i !== 0) {
          args += ', ';
          types += ', ';
        }
        types += ("String arg" + i);
        args += ("arg" + i);
      }
    }

    if (cmd[1] < 0) {
      types = "List<String> args";
      args = "args";
    }

    commands.push({
      enum: cmd[0].replace('-', '_').replace(':', '').toUpperCase(),
      name: cmd[0],
      safename: cmd[0].replace('-', ' ').replace(':', '').toUpperCase(),
      arity: cmd[1],
      variable: cmd[1] < 0,
      argLen: cmd[1] - 1,
      args: args,
      types: types,
      firstKey: cmd[3],
      lastKey: cmd[4],
      multiKey: cmd[4] < 0,
      interval: cmd[5],
      keyless: cmd[5] === 0 && cmd[2].indexOf('movablekeys') === -1,
      readOnly: cmd[2].indexOf('readonly') !== -1,
      movable: cmd[2].indexOf('movablekeys') !== -1
    });
  });

  // var cmd_template = Handlebars.compile(fs.readFileSync('redis-command.hbs', 'utf8'));
  //
  // fs.writeFileSync(
  //   '../src/main/java/io/vertx/redis/client/Command.java',
  //   cmd_template(commands));

  var api_template = Handlebars.compile(fs.readFileSync('redis-api.hbs', 'utf8'));

  fs.writeFileSync(
    '../src/main/java/io/vertx/redis/client/RedisAPI.java',
    api_template(commands));

  process.exit(0);
});
