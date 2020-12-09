var redis = require('redis').createClient(7006);

redis.hmset(['mykey', 'other', 'NIL'], function (err, res) {
  redis.quit();
  if (err) {
    return console.error(err);
  }
  process.exit(0);
});
