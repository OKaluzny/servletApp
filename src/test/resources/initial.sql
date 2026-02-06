CREATE TABLE IF NOT EXISTS users
(
    id      Serial primary key,
    name    VARCHAR(255),
    email   VARCHAR(255),
    country VARCHAR(255)
);
