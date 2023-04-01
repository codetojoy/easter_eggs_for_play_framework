-- Posts table

-- !Ups

CREATE TABLE post(
id SERIAL,
title VARCHAR(256) NOT NULL,
content VARCHAR(256) NOT NULL,
constraint pk_post primary key (id)
);

INSERT into post (title, content) VALUES ('abc 123','the quick brown fox ...       '); 
INSERT into post (title, content) VALUES ('def 456','jumped over ...       '); 
INSERT into post (title, content) VALUES ('ijk 789','the lazy dog ...       '); 

-- !Downs

DROP TABLE post;

