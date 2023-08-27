CREATE TABLE if not exists users
(
    id      Serial primary key,
    name    VARCHAR(255),
    email   VARCHAR(255),
    country VARCHAR(255)
);