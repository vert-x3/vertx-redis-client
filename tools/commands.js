import * as fs from 'fs'
import * as redis from 'redis'
import Handlebars from 'handlebars'
import HandlebarsHelpers from 'handlebars-helpers'

const excludedPubSub = ['PUBSUB', 'PUBLISH', 'SPUBLISH']

let client = await redis.createClient().connect()

let version = null
let infoResponse = await client.INFO()
for (let line of infoResponse.split("\r\n")) {
  if (line.startsWith('redis_version')) {
    version = line
  }
}

let docs = {}
let commandDocsResponse = await client.sendCommand(['COMMAND', 'DOCS'])
for (let i = 0; i < commandDocsResponse.length; i += 2) {
  let name = commandDocsResponse[i]
  let docResponse = commandDocsResponse[i + 1]

  let doc = {}
  for (let j = 0; j < docResponse.length; j += 2) {
    doc[docResponse[j]] = docResponse[j + 1]
  }

  let summary = doc['summary'] || null
  if (summary != null) {
    summary = summary.trim()
    if (!summary.endsWith('.')) {
      summary += '.'
    }
  }

  let deprecated = false
  if (doc['doc_flags']) {
    for (let flag of doc['doc_flags']) {
      if (flag === 'deprecated') {
        deprecated = true
        break
      }
    }
  }
  let deprecatedSince = null
  let replacedBy = null
  if (deprecated) {
    deprecatedSince = doc['deprecated_since'] || 'unknown'
    replacedBy = doc['replaced_by'] || 'unknown'
    replacedBy = replacedBy.replaceAll(/`(.*?)`/g, '{@code $1}')
  }

  docs[name] = {
    summary,
    deprecatedSince,
    replacedBy
  }
}

let commands = []
let commandInfoResponse = await client.sendCommand(['COMMAND', 'INFO'])
commandInfoResponse.sort((a, b) => {
  return a[0] < b[0] ? -1 : a[0] > b[0] ? 1 : 0
})
for (let cmd of commandInfoResponse) {
  let types = ""
  let args = ""
  let argLen = 0

  let identifier = cmd[0].replace('.', '_').replace('-', '_').replace(':', '').toUpperCase()

  if (cmd[1] > 0) {
    for (let i = 0; i < cmd[1] - 1; i++) {
      if (i !== 0) {
        args += ', '
        types += ', '
      }
      types += ("String arg" + i)
      args += ("arg" + i)
    }
    // arg len includes the command name
    argLen = cmd[1]
    if (argLen) {
      argLen--
    }
  }

  if (cmd[1] < 0) {
    types = "List<String> args"
    args = "args"
    argLen = Math.abs(cmd[1])
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
    pubsub: cmd[2].indexOf('pubsub') !== -1 && !excludedPubSub.includes(identifier),
    docsSummary: docs[cmd[0]].summary,
    docsDeprecatedSince: docs[cmd[0]].deprecatedSince,
    docsReplacedBy: docs[cmd[0]].replacedBy,
  })
}

await client.disconnect()

HandlebarsHelpers()
let template = Handlebars.compile(fs.readFileSync('redis-api.hbs', 'utf8'))
let rendered = template({
  commands,
  version,
})
fs.writeFileSync('../src/main/java/io/vertx/redis/client/RedisAPI.java', rendered)
