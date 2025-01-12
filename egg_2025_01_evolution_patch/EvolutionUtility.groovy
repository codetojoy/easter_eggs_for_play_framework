
@Grapes(
    @Grab(group='com.google.guava', module='guava', version='33.4.0-jre')
)

import com.google.common.io.BaseEncoding
import java.security.MessageDigest

// Usage
// 1. run the app so that the evolutions are applied
// 2. halt the app
// 3. edit 3.sql with some modifications
// 4. run `groovy EvolutionUtility.groovy`
// 5. apply the output SQL to the database
// 6. run the app and confirm 

class Info {
    def ups = ""
    def downs = ""
}

def MODE_UPS = 1
def MODE_DOWNS = 2
def MODE_UNKNOWNS = -1
def mode = MODE_UNKNOWNS

// from: https://github.com/playframework/playframework/blob/6c23106bbf39fc723c37c97d0aca683aafd2184b/persistence/play-jdbc-evolutions/src/main/scala/play/api/db/evolutions/EvolutionsApi.scala#L692

def upsMarker   = /^(#|--).*!Ups.*$/
def downsMarker = /^(#|--).*!Downs.*$/

// from:
// (1) https://github.com/playframework/playframework/blob/6c23106bbf39fc723c37c97d0aca683aafd2184b/persistence/play-jdbc-evolutions/src/main/scala/play/api/db/evolutions/Evolutions.scala#L41
// (2) https://github.com/playframework/playframework/blob/6c23106bbf39fc723c37c97d0aca683aafd2184b/core/play/src/main/scala/play/api/libs/Codecs.scala#L17
// (3) https://github.com/playframework/playframework/blob/6c23106bbf39fc723c37c97d0aca683aafd2184b/core/play/src/main/scala/play/api/libs/Codecs.scala#L26

def sha1MessageDigest = MessageDigest.getInstance("SHA-1")
def hexEncoder = BaseEncoding.base16().lowerCase()

def getHash = { info ->
    def s = info.downs.trim() + info.ups.trim()
    def preHash = sha1MessageDigest.digest(s.bytes)
    def hash = hexEncoder.encode(preHash)
    return hash
}

// ------------ main

def file = new File("./conf/evolutions/default/3.sql")

def sql = file.getText()

def info = new Info()

// inspired by: https://github.com/playframework/playframework/blob/6c23106bbf39fc723c37c97d0aca683aafd2184b/persistence/play-jdbc-evolutions/src/main/scala/play/api/db/evolutions/EvolutionsApi.scala#L692

sql.eachLine { line ->
    if (line ==~ upsMarker) {
        mode = MODE_UPS
    } else if (line ==~ downsMarker) {
        mode = MODE_DOWNS
    } else {
        if (mode == MODE_UPS) {
            info.ups = info.ups + line.trim() + "\n"
        } else if (mode == MODE_DOWNS) {
            info.downs = info.downs + line.trim() + "\n"
        }
    }
}

def hash = getHash(info)
def id = 3

println """
UPDATE play_evolutions
set hash = '${hash}',
revert_script = '${info.downs}',
apply_script = '${info.ups}'
where id = ${id};
"""

/*
println "ups: '${info.ups}'"
println "downs: '${info.downs}'"
println "file: ${file.name} hash: ${hash}"
println "Ready."
*/
