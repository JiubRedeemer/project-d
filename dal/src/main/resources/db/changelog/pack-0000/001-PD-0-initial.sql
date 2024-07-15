CREATE TABLE core.users
(
    id       uuid NOT NULL,
    username text NOT NULL,
    email    text NOT NULL,
    password text NOT NULL,
    salt     text NOT NULL,
    role     text NOT NULL,
    PRIMARY KEY (id)
);
