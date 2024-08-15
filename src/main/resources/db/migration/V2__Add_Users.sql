create table if not exists users (
    id varchar(36) not null,
    email varchar(100) not null,
    password varchar(200) not null,
    primary key (id)
);