var fs = require('fs');

var redis = require('redis').createClient(6379);
var Handlebars = require('handlebars');
require('handlebars-helpers')();

const excludedPubSub = ['PUBSUB', 'PUBLISH', 'SPUBLISH'];

redis.info((err, info) => {
  if (err) {
    console.error(err);
    process.exit(1);
  }

  let version;

  info
    .split("\r\n")
    .forEach(el => {
      if (el.startsWith('redis_version')) {
        version = el;
      }
    });

  redis.command((err, res) => {
    redis.quit();
    if (err) {
      return console.error(err);
    }

    // Ensure stable sorting of commands to avoid git diff churn every time we regenerate
    res.sort((a, b) => {
      return a[0] < b[0] ? -1 : a[0] > b[0] ? 1 : 0;
    });

    var commands = [];

    res.forEach((cmd) => {
      let types = "";
      let args = "";
      let argLen = 0;

      let identifier = cmd[0].replace('.', '_').replace('-', '_').replace(':', '').toUpperCase();

      if (cmd[1] > 0) {
        for (let i = 0; i < cmd[1] - 1; i++) {
          if (i !== 0) {
            args += ', ';
            types += ', ';
          }
          types += ("String arg" + i);
          args += ("arg" + i);
        }
        // arg len includes the command name
        argLen = cmd[1];
        if (argLen) {
          argLen--;
        }
      }

      if (cmd[1] < 0) {
        types = "List<String> args";
        args = "args";
        argLen = Math.abs(cmd[1]);
      }

      commands.push({
        enum: identifier,
        name: cmd[0],
        safename: cmd[0].replace('-', ' ').replace(':', '').toUpperCase(),
        arity: cmd[1],
        variable: cmd[1] < 0,
        argLen: argLen,
        args: args,
        types: types,
        firstKey: cmd[3],
        lastKey: cmd[4],
        multiKey: cmd[4] < 0,
        interval: cmd[5],
        keyless: cmd[5] === 0 && cmd[2].indexOf('movablekeys') === -1,
        write: cmd[2].indexOf('write') !== -1,
        readOnly: cmd[2].indexOf('readonly') !== -1,
        movable: cmd[2].indexOf('movablekeys') !== -1,
        pubsub: cmd[2].indexOf('pubsub') !== -1 && !excludedPubSub.includes(identifier)
      });
    });

    commands.version = version;

    var api_template = Handlebars.compile(fs.readFileSync('redis-api.hbs', 'utf8'));

    fs.writeFileSync(
      '../src/main/java/io/vertx/redis/client/RedisAPI.java',
      api_template(commands));

    process.exit(0);
  });
});
