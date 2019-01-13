CREATE TABLE users (
    id                    UUID        PRIMARY KEY       NOT NULL,
    username              VARCHAR     UNIQUE            NOT NULL,
    first_name            VARCHAR                       NOT NULL,
    last_name             varchar                       NOT NULL
)