CREATE TABLE core.users
(
    id       uuid  NOT NULL,
    username text  NOT NULL,
    email    text  NOT NULL,
    password text  NOT NULL,
    roles    jsonb NOT NULL,
    PRIMARY KEY (id)
);

comment on column core.users.id is 'Идентификатор пользователя';
comment on column core.users.username is 'Имя пользователя';
comment on column core.users.email is 'Емаил пользователя';
comment on column core.users.password is 'Пароль пользователя в зашифрованном виде';
comment on column core.users.roles is 'Список ролей пользователя, например [USER, ADMIN]';

create unique index users_email_uindex on core.users (email);
comment on index core.users_email_uindex is 'Индекс уникальности Email';

create unique index users_username_uindex on core.users (username);
comment on index core.users_email_uindex is 'Индекс уникальности Имени пользователя';
