create table userss (
    id text primary key unique not null,
    username text not null unique,
    password text not null,
    role text not null
);
