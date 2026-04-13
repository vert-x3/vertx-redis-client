import * as fs from 'fs'
import * as redis from 'redis'
import Docker from 'dockerode'
import Handlebars from 'handlebars'
import HandlebarsHelpers from 'handlebars-helpers'

const excludedPubSub = ['pubsub', 'publish', 'spublish']

const moduleNames = {
  bf: 'Bloom',
  ReJSON: 'ReJSON',
  search: 'Search',
  timeseries: 'TimeSeries',
  vectorset: 'VectorSet',
}

// oldest first, newest last
const redisImages = [
  // the Redis Stack release our code has originally been based upon
  'redis/redis-stack:7.0.6-RC9',

  // ignore Redis Stack 7.2 and 7.4, because they contain commands
  // that we never exposed and that do not exist in Redis 8

  'redis:8.0.6',
  'redis:8.2.5',
  'redis:8.4.2',
  'redis:8.6.2',
]

const docker = new Docker()

async function startContainer(image) {
  await new Promise((resolve, reject) => {
    docker.pull(image, (err, stream) => {
      if (err) return reject(err)
      docker.modem.followProgress(stream, (err) => err ? reject(err) : resolve())
    })
  })
  let container = await docker.createContainer({
    Image: image,
    ExposedPorts: { '6379/tcp': {} },
    HostConfig: { PortBindings: { '6379/tcp': [{ HostPort: '0' }] } },
  })
  await container.start()
  await new Promise(r => setTimeout(r, 1000)) // wait a bit until it's actually started
  let info = await container.inspect()
  let port = info.NetworkSettings.Ports['6379/tcp'][0].HostPort
  return { container, port }
}

async function fetchCommands(port) {
  let url = 'redis://localhost:' + port
  let client = await redis.createClient({ url, RESP: 3 }).connect()

  // get Redis version
  let version = null
  let infoResponse = await client.sendCommand(['INFO'])
  for (let line of infoResponse.split("\r\n")) {
    if (line.startsWith('redis_version:')) {
      version = line
      break
    }
  }

  // get command docs
  let docs = {}
  let commandDocsResponse = await client.sendCommand(['COMMAND', 'DOCS'])
  for (let [name, doc] of Object.entries(commandDocsResponse)) {
    let summary = doc.summary || null
    if (summary != null) {
      summary = summary.trim()
      if (!summary.endsWith('.')) {
        summary += '.'
      }
    }

    let deprecated = false
    for (let flag of (doc.doc_flags || [])) {
      if (flag === 'deprecated') {
        deprecated = true
        break
      }
    }

    let deprecatedSince = null
    let replacedBy = null
    if (deprecated) {
      deprecatedSince = doc.deprecated_since || 'unknown'
      replacedBy = doc.replaced_by || 'unknown'
      replacedBy = replacedBy.replaceAll(/`(.*?)`/g, '{@code $1}')
    }

    let since = doc.since || null
    let module = moduleNames[doc.module] || doc.module || null

    docs[name] = { summary, since, module, deprecatedSince, replacedBy }
  }

  // get command info
  let commands = {}
  let commandInfoResponse = await client.sendCommand(['COMMAND', 'INFO'])
  for (let cmd of commandInfoResponse) {
    let name = cmd[0]
    // skip internal commands (e.g. _FT.SAFEADD)
    if (name.startsWith('_')) continue
    let arity = cmd[1]
    let flags = cmd[2]
    let keySpecs = cmd[8]

    // read-only flag for Command.java
    let ro = null
    if (flags.some(f => f.toLowerCase() === 'readonly')) {
      ro = true
    }
    if (flags.some(f => f.toLowerCase() === 'write')) {
      ro = false
    }

    // pubsub flag
    let pubSub = false
    if (flags.some(f => f.toLowerCase() === 'pubsub') && !excludedPubSub.includes(name.toLowerCase())) {
      pubSub = true
    }

    // key locators for Command.java
    let getKeys = false
    let keyLocator = null
    for (let hint of keySpecs) {
      let flagRO = null
      let beginSearch = null
      let findKeys = null

      if (hint.flags.includes('RO')) {
        flagRO = true
      } else if (hint.flags.some(flag => flag === 'RW' || flag === 'OW' || flag === 'RM')) {
        flagRO = false
      }

      if (hint.begin_search) {
        let spec = hint.begin_search.spec
        switch (hint.begin_search.type) {
          case 'index':
            beginSearch = 'new BeginSearchIndex(' + spec.index + ')'
            break
          case 'keyword':
            beginSearch = 'new BeginSearchKeyword("' + spec.keyword + '", ' + spec.startfrom + ')'
            break
          case 'unknown':
            getKeys = true
            break
        }
      }

      if (hint.find_keys) {
        let spec = hint.find_keys.spec
        switch (hint.find_keys.type) {
          case 'range':
            findKeys = 'new FindKeysRange(' + spec.lastkey + ', ' + spec.keystep + ', ' + spec.limit + ')'
            break
          case 'keynum':
            findKeys = 'new FindKeysKeynum(' + spec.keynumidx + ', ' + spec.firstkey + ', ' + spec.keystep + ')'
            break
          case 'unknown':
            getKeys = true
            break
        }
      }

      if (beginSearch != null && findKeys != null) {
        let locator = 'new KeyLocator(' + flagRO + ', ' + beginSearch + ', ' + findKeys + ')'
        if (keyLocator == null) {
          keyLocator = locator
        } else {
          keyLocator += ', ' + locator
        }
      }
    }

    let doc = docs[name] || {}

    commands[name] = {
      name,
      arity,
      ro,
      pubSub,
      getKeys,
      keyLocator,
      doc,
    }
  }

  client.destroy()
  return { version, commands }
}

async function fetchAndMerge(images) {
  let results = []
  for (let image of images) {
    console.log('Using ' + image)
    let { container, port } = await startContainer(image)
    results.push(await fetchCommands(port))
    await container.stop()
    await container.remove()
  }

  // the primary (newest) instance is the last one
  let version = results[results.length - 1].version

  // merge: start with all commands from the newest instance,
  // then add commands from older instances that are missing in the newest
  let merged = { ...results[results.length - 1].commands }
  for (let i = results.length - 2; i >= 0; i--) {
    for (let [name, cmd] of Object.entries(results[i].commands)) {
      if (!(name in merged)) {
        console.log(name + ' only in ' + images[i])
        cmd.doc.removed = true
        merged[name] = cmd
      }
    }
  }

  return { version, merged, oldest: results[0].commands }
}

let redisResult = await fetchAndMerge(redisImages)

let version = redisResult.version

// build command list from a merged command map;
// oldest is optional, used for detecting arity changes and generating compat overloads
function buildCommandList(merged, oldest) {
  let commands = []
  for (let cmd of Object.values(merged)) {
    let { name, arity, ro, pubSub, getKeys, keyLocator, doc } = cmd
    let identifier = name.replaceAll('.', '_').replaceAll('-', '_').toUpperCase()

    // build Command.java declaration
    let declaration = ''
    if (doc.summary || doc.since || doc.removed || doc.deprecatedSince) {
      declaration += '  /**\n'
      if (doc.summary) {
        declaration += '   * ' + doc.summary + '\n'
      }
      if (doc.since) {
        declaration += '   * @since Redis ' + (doc.module ? doc.module + ' ' : '') + doc.since + '\n'
      }
      if (doc.removed) {
        declaration += '   * @deprecated this command no longer exists in the latest Redis release\n'
      } else if (doc.deprecatedSince) {
        declaration += '   * @deprecated since Redis ' + doc.deprecatedSince + ', replaced by: ' + doc.replacedBy + '\n'
      }
      declaration += '   */\n'
    }
    declaration += '  Command ' + identifier + ' = new CommandImpl("' + name + '", ' + arity + ', ' + ro + ', ' + pubSub + ', ' + getKeys
    if (keyLocator) {
      declaration += ', ' + keyLocator
    }
    declaration += ');'

    // build CommandMap.java entry
    let mapEntry = '    KNOWN_COMMANDS.put("' + name.toLowerCase() + '", Command.' + identifier + ');'

    // build API method signature fields
    let types = ""
    let args = ""
    let argLen = 0
    if (arity > 0) {
      for (let i = 0; i < arity - 1; i++) {
        if (i !== 0) {
          args += ', '
          types += ', '
        }
        types += ("String arg" + i)
        args += ("arg" + i)
      }
      argLen = arity - 1
    } else if (arity < 0) {
      types = "List<String> args"
      args = "args"
      argLen = Math.abs(arity)
    }

    // build compat overload if arity changed between oldest and newest
    let oldTypes = null
    let oldArgs = null
    let oldArgLen = 0
    let oldVariable = false
    if (oldest) {
      let oldCmd = oldest[name]
      if (oldCmd && oldCmd.arity !== arity) {
        let oldArity = oldCmd.arity
        oldTypes = ""
        oldArgs = ""
        if (oldArity > 0) {
          for (let j = 0; j < oldArity - 1; j++) {
            if (j !== 0) {
              oldArgs += ', '
              oldTypes += ', '
            }
            oldTypes += ("String arg" + j)
            oldArgs += ("arg" + j)
          }
          oldArgLen = oldArity - 1
        } else if (oldArity < 0) {
          oldTypes = "List<String> args"
          oldArgs = "args"
          oldArgLen = Math.abs(oldArity)
          oldVariable = true
        }
        // skip if signatures would be identical
        if (oldTypes === types) {
          oldTypes = null
          oldArgs = null
          oldArgLen = 0
          oldVariable = false
        }
      }
    }

    commands.push({
      declaration,
      mapEntry,
      // API template fields
      name,
      identifier,
      variable: arity < 0,
      argLen,
      args,
      types,
      oldVariable,
      oldArgLen,
      oldArgs,
      oldTypes,
      docsSummary: doc.summary,
      docsSince: doc.since,
      docsSinceServer: 'Redis',
      docsModule: doc.module,
      docsRemoved: doc.removed,
      docsDeprecatedSince: doc.deprecatedSince,
      docsReplacedBy: doc.replacedBy,
    })
  }
  return commands
}

let redisCommandList = buildCommandList(redisResult.merged, redisResult.oldest)

let byIdentifier = [...redisCommandList].sort((a, b) => a.identifier < b.identifier ? -1 : a.identifier > b.identifier ? 1 : 0)

let commandDeclarations = byIdentifier.map(c => c.declaration).join('\n')
let commandTemplate = Handlebars.compile(fs.readFileSync('command.hbs', 'utf8'))
fs.writeFileSync('../src/main/java/io/vertx/redis/client/Command.java',
  commandTemplate({ version, commandDeclarations }))

let commandMapEntries = byIdentifier.map(c => c.mapEntry).join('\n')
let commandMapTemplate = Handlebars.compile(fs.readFileSync('command-map.hbs', 'utf8'))
fs.writeFileSync('../src/main/java/io/vertx/redis/client/impl/CommandMap.java',
  commandMapTemplate({ commandMapEntries }))

HandlebarsHelpers()

let redisByName = [...redisCommandList].sort((a, b) => a.name < b.name ? -1 : a.name > b.name ? 1 : 0)
let redisApiTemplate = Handlebars.compile(fs.readFileSync('redis-api.hbs', 'utf8'))
fs.writeFileSync('../src/main/java/io/vertx/redis/client/RedisAPI.java',
  redisApiTemplate({ version: redisResult.version, commands: redisByName }))
