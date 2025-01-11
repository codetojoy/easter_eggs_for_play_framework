# --- !Ups

create table system_user (
  id                        bigint not null,
  name                      varchar(255),
  email                     varchar(255),
  address                   varchar(255),
  constraint pk_system_user primary key (id))
;

# --- !Downs
