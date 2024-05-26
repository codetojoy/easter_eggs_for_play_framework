
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

sql.execute("""
CREATE TABLE book ( 
id SERIAL PRIMARY KEY,
title VARCHAR(256) NOT NULL,
author VARCHAR(256) NOT NULL
);
ALTER TABLE book ADD CONSTRAINT unique_title UNIQUE (title, author);
""")

sql.execute("""
CREATE TABLE topic ( 
id SERIAL PRIMARY KEY,
name VARCHAR(256) NOT NULL
);
ALTER TABLE topic ADD CONSTRAINT unique_name UNIQUE (name);
""")

sql.execute("""
CREATE TABLE book_topic ( 
id SERIAL PRIMARY KEY,
book_id INT NOT NULL, 
topic_id INT NOT NULL,
description VARCHAR(256) NOT NULL
);

ALTER TABLE book_topic ADD CONSTRAINT fk_book_topic_book FOREIGN KEY(book_id) REFERENCES book(id);
ALTER TABLE book_topic ADD CONSTRAINT fk_book_topic_topic FOREIGN KEY(topic_id) REFERENCES topic(id);
""")


sql.close()

