# --- !Ups

create table item (
  id                        bigint not null,
  sku                       varchar(255),
  description               varchar(255),
  oem                       varchar(255),
  constraint pk_item primary key (id))
;

# --- !Downs
