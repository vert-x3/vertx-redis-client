import * as fs from 'fs'
import * as redis from 'redis'
import Handlebars from 'handlebars'
import HandlebarsHelpers from 'handlebars-helpers'

const excludedPubSub = ['pubsub', 'publish', 'spublish']

let client = await redis.createClient({ RESP : 3}).connect()

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

  docs[name] = { summary, deprecatedSince, replacedBy }
}

// get command info
let commands = []
let commandInfoResponse = await client.sendCommand(['COMMAND', 'INFO'])
for (let cmd of commandInfoResponse) {
  let name = cmd[0]
  let arity = cmd[1]
  let flags = cmd[2]
  let keySpecs = cmd[8]

  let identifier = name.replaceAll('.', '_').replaceAll('-', '_').toUpperCase()

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

  // build Command.java declaration
  let declaration = ''
  if (doc.summary) {
    declaration += '  /**\n'
    declaration += '   * ' + doc.summary + '\n'
    if (doc.deprecatedSince) {
      declaration += '   * @deprecated since: ' + doc.deprecatedSince + ', replaced by: ' + doc.replacedBy + '\n'
    }
    declaration += '   */\n'
  }
  if (doc.deprecatedSince) {
    declaration += '  @Deprecated\n'
  }
  declaration += '  Command ' + identifier + ' = new CommandImpl("' + name + '", ' + arity + ', ' + ro + ', ' + pubSub + ', ' + getKeys
  if (keyLocator) {
    declaration += ', ' + keyLocator
  }
  declaration += ');'

  // build CommandMap.java entry
  let mapEntry = '    KNOWN_COMMANDS.put("' + name.toLowerCase() + '", Command.' + identifier + ');'

  // build RedisAPI.java method signature fields
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

  commands.push({
    declaration,
    mapEntry,
    // RedisAPI template fields
    name,
    identifier,
    variable: arity < 0,
    argLen,
    args,
    types,
    docsSummary: doc.summary,
    docsDeprecatedSince: doc.deprecatedSince,
    docsReplacedBy: doc.replacedBy,
  })
}

client.destroy()

let byName = [...commands].sort((a, b) => a.name < b.name ? -1 : a.name > b.name ? 1 : 0)
let byIdentifier = [...commands].sort((a, b) => a.identifier < b.identifier ? -1 : a.identifier > b.identifier ? 1 : 0)

let commandDeclarations = byIdentifier.map(c => c.declaration).join('\n')
let commandTemplate = Handlebars.compile(fs.readFileSync('command.hbs', 'utf8'))
fs.writeFileSync('../src/main/java/io/vertx/redis/client/Command.java',
  commandTemplate({ version, commandDeclarations }))

let commandMapEntries = byIdentifier.map(c => c.mapEntry).join('\n')
let commandMapTemplate = Handlebars.compile(fs.readFileSync('command-map.hbs', 'utf8'))
fs.writeFileSync('../src/main/java/io/vertx/redis/client/CommandMap.java',
  commandMapTemplate({ commandMapEntries }))

HandlebarsHelpers()
let redisApiTemplate = Handlebars.compile(fs.readFileSync('redis-api.hbs', 'utf8'))
fs.writeFileSync('../src/main/java/io/vertx/redis/client/RedisAPI.java',
  redisApiTemplate({ version, commands: byName }))
