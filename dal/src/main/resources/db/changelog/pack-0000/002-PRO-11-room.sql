-- Создание таблицы core.rooms
CREATE TABLE core.rooms
(
    id                     uuid                              NOT NULL,
    name                   text      DEFAULT 'Комната'::text NOT NULL,
    owner_id               uuid                              NOT NULL,
    create_datetime        timestamp DEFAULT now()           NOT NULL,
    update_datetime        timestamp DEFAULT now()           NOT NULL,
    delete_datetime        timestamp,
    last_activity_datetime timestamp DEFAULT now()           NOT NULL,
    CONSTRAINT rooms_pkey PRIMARY KEY (id)
);

-- Добавление комментариев для таблицы core.rooms и её столбцов
COMMENT ON TABLE core.rooms IS 'Игровая комната';
COMMENT ON COLUMN core.rooms.id IS 'Уникальный идентификатор комнаты в формате UUID';
COMMENT ON COLUMN core.rooms.name IS 'Название комнаты. Значение по умолчанию — "Комната"';
COMMENT ON COLUMN core.rooms.owner_id IS 'Идентификатор владельца комнаты в формате UUID';
COMMENT ON COLUMN core.rooms.create_datetime IS 'Время создания записи. По умолчанию устанавливается на текущее время';
COMMENT ON COLUMN core.rooms.update_datetime IS 'Время последнего обновления записи. По умолчанию устанавливается на текущее время';
COMMENT ON COLUMN core.rooms.delete_datetime IS 'Время удаления записи, если она была удалена. Может быть NULL';
COMMENT ON COLUMN core.rooms.last_activity_datetime IS 'Время последней активности в комнате. По умолчанию устанавливается на текущее время';

-- Создание таблицы core.rooms_users
CREATE TABLE core.rooms_users
(
    user_id uuid  NOT NULL,
    room_id uuid  NOT NULL,
    roles   jsonb NOT NULL,
    PRIMARY KEY (user_id, room_id)
);

-- Добавление комментариев для таблицы core.rooms_users и её столбцов
COMMENT ON TABLE core.rooms_users IS 'Таблица связи пользователей и комнат';
COMMENT ON COLUMN core.rooms_users.user_id IS 'Уникальный идентификатор пользователя';
COMMENT ON COLUMN core.rooms_users.room_id IS 'Уникальный идентификатор комнаты';
COMMENT ON COLUMN core.rooms_users.roles IS 'Список ролей пользователя в комнате, например: [MASTER, PLAYER, MODERATOR]';

-- Создание индексов
CREATE INDEX rooms_owner_id ON core.rooms (owner_id);
CREATE INDEX rooms_users_room_id ON core.rooms_users (room_id, user_id);

-- Добавление внешних ключей
ALTER TABLE core.rooms_users
    ADD CONSTRAINT room_user_FK FOREIGN KEY (room_id) REFERENCES core.rooms (id);
ALTER TABLE core.rooms
    ADD CONSTRAINT rooms_owner_id_fkey FOREIGN KEY (owner_id) REFERENCES core.users (id) ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE core.rooms_users
    ADD CONSTRAINT FKrooms_user134359 FOREIGN KEY (user_id) REFERENCES core.users (id);
