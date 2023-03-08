# Vert.x Redis Client

[![Build Status (5.x)](https://github.com/vert-x3/vertx-redis-client/actions/workflows/ci-5.x.yml/badge.svg)](https://github.com/vert-x3/vertx-redis-client/actions/workflows/ci-5.x.yml)
[![Build Status (4.x)](https://github.com/vert-x3/vertx-redis-client/actions/workflows/ci-4.x.yml/badge.svg)](https://github.com/vert-x3/vertx-redis-client/actions/workflows/ci-4.x.yml)

The Vert.x Redis client provides an asynchronous API to interact with a [Redis](http://redis.io) data-structure server.

## Running the tests

In order to run the tests, it is expected that a simple cluster is setup in the test environment, you can do this by
running the following container:

```bash
docker run -d \
  -e 'IP=127.0.0.1' \
  -e 'STANDALONE=true' \
  -e 'SENTINEL=true' \
  -p '7000-7007:7000-7007' \
  -p '5000-5002:5000-5002' \
  grokzen/redis-cluster:6.2.0
```

## Documentation:

The documentation can be read from the official vert.x [Documentation](http://vertx.io/docs/vertx-redis-client/java/).
