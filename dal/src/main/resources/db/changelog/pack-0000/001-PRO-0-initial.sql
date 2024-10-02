-- Создание таблицы core.users
CREATE TABLE core.users
(
    id               uuid      NOT NULL,
    username         text      NOT NULL UNIQUE,
    email            text      NOT NULL UNIQUE,
    registration_date timestamp NOT NULL UNIQUE,
    password         text      NOT NULL,
    roles            jsonb     NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

-- Добавление комментариев для столбцов таблицы core.users
COMMENT ON COLUMN core.users.id IS 'Идентификатор пользователя';
COMMENT ON COLUMN core.users.username IS 'Имя пользователя';
COMMENT ON COLUMN core.users.registration_date IS 'Дата регистрации пользователя';
COMMENT ON COLUMN core.users.email IS 'Емаил пользователя';
COMMENT ON COLUMN core.users.password IS 'Пароль пользователя в зашифрованном виде';
COMMENT ON COLUMN core.users.roles IS 'Список ролей пользователя, например [USER, ADMIN]';

-- Создание уникальных индексов
CREATE UNIQUE INDEX users_username_uindex ON core.users (username);
CREATE UNIQUE INDEX users_email_uindex ON core.users (email);
