CREATE TABLE users
(
    id      Serial NOT NULL,
    name    VARCHAR(255),
    email   VARCHAR(255),
    country VARCHAR(255),
    PRIMARY KEY (id)
)