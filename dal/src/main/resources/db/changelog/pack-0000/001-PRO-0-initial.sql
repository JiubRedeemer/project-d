-- Создание таблицы core.users
CREATE TABLE core.user
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
COMMENT ON COLUMN core.user.id IS 'Идентификатор пользователя';
COMMENT ON COLUMN core.user.username IS 'Имя пользователя';
COMMENT ON COLUMN core.user.registration_date IS 'Дата регистрации пользователя';
COMMENT ON COLUMN core.user.email IS 'Емаил пользователя';
COMMENT ON COLUMN core.user.password IS 'Пароль пользователя в зашифрованном виде';
COMMENT ON COLUMN core.user.roles IS 'Список ролей пользователя, например [USER, ADMIN]';

-- Создание уникальных индексов
CREATE UNIQUE INDEX user_username_uindex ON core.user (username);
CREATE UNIQUE INDEX user_email_uindex ON core.user (email);
