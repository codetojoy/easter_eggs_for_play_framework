
@Grapes(
    @Grab(group='org.postgresql', module='postgresql', version='42.6.0')
)
@GrabConfig(systemClassLoader=true)

import groovy.sql.*
import org.postgresql.*

def myGetEnv = { envVar ->
    def result = System.getenv(envVar)
    assert result
    assert !result.isEmpty()
    return result
}

final String DB_NAME = myGetEnv("DB_NAME")
final String DB_USERNAME = myGetEnv("DB_USERNAME")
final String DB_PASSWORD = myGetEnv("DB_PASSWORD")

def sql = Sql.newInstance("jdbc:postgresql://127.0.0.1:5432/$DB_NAME", DB_USERNAME, DB_PASSWORD, "org.postgresql.Driver")

assert args.size() > 0 
def table = args[0]
println "table : " + table
sql.eachRow("SELECT * FROM " + table) { println it }

sql.close()

