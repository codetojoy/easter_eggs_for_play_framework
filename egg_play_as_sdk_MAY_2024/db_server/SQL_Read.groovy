
@Grapes(
    @Grab(group='org.postgresql', module='postgresql', version='42.6.0')
)
@GrabConfig(systemClassLoader=true)

import groovy.sql.*
import org.postgresql.*

def myGetEnv = { envVar ->
    def result = System.getenv(envVar)
    assert result
    return result
}

final String DB_NAME = myGetEnv("DB_NAME")
final String DB_USERNAME = myGetEnv("DB_USERNAME")
final String DB_PASSWORD = myGetEnv("DB_PASSWORD")

def user = DB_USERNAME
def password = DB_PASSWORD
def host = "127.0.0.1"
def port = 5432
def database = DB_NAME

def sql = Sql.newInstance("jdbc:postgresql://${host}:${port}/${database}", user, password, "org.postgresql.Driver")

// models

sql.eachRow("""
SELECT b.title as title, b.author as author, t.name as topic, bt.description as reason from book_topic bt
JOIN book b on b.id = bt.book_id
JOIN topic t on t.id = bt.topic_id 
ORDER BY b.title
""") {
    println "TRACER title: ${it.title} author: ${it.author} topic: ${it.topic} reason: ${it.reason}"
}

sql.close()
