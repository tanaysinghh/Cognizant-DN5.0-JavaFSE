-- Run this in MySQL before starting the app (schema name: ormlearn)
create schema if not exists ormlearn;
use ormlearn;

create table country(
    co_code varchar(2) primary key,
    co_name varchar(50)
);

insert into country values ('IN', 'India');
insert into country values ('US', 'United States of America');
