
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

class Book {
    def title
    def author
}

class Topic {
    def name
    def description
}

def selectId(def sql, def statement, def params) {
    def id = null
    def rows = sql.rows(statement, params)

    if (rows.size() == 1) {
        id = rows[0].id
    }

    return id
}

// populate 

def myExecute(def sql, def book, def topics) {
    def insert

    insert = " INSERT INTO book (title, author) VALUES (?,?); "
    sql.execute(insert, [book.title, book.author]) 

    def bookId = selectId(sql, "SELECT id from book where title = ? and author = ?", [book.title, book.author]);

    topics.each { topic ->
        // does topic already exist ?
        def topicId = selectId(sql, "SELECT id from topic where name = ?", [topic.name]);

        if (topicId == null) {
            insert = " INSERT INTO topic (name) VALUES (?); "
            sql.execute(insert, [topic.name]) 
            topicId = selectId(sql, "SELECT id from topic where name = ?", [topic.name]);
        }

        insert = " INSERT INTO book_topic (book_id, topic_id, description) VALUES (?,?,?); "
        sql.execute(insert, [bookId, topicId, topic.description]) 
    }
}

def id

myExecute(sql, new Book(title: "Undoing Project", author: "Michael Lewis"), 
            [
                new Topic(name: "behavioral economics", description: "reason 1ml" ),
                new Topic(name: "cognitive psychology", description: "reason 2ml" ),
                new Topic(name: "non-fiction", description: "reason 3ml" )
            ])

myExecute(sql, new Book(title: "What The Dog Saw", author: "Malcolm Gladwell"), 
            [
                new Topic(name: "compilation", description: "reason 1mg" ),
                new Topic(name: "The New Yorker", description: "reason 2mg" ),
                new Topic(name: "non-fiction", description: "reason 3mg" )
            ])

sql.close()
