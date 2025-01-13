-- Создание таблицы core.room
CREATE TABLE core.room
(
    id                     uuid                              NOT NULL,
    name                   text      DEFAULT 'Комната'::text NOT NULL,
    description            text,
    owner_id               uuid                              NOT NULL,
    create_datetime        timestamp DEFAULT now()           NOT NULL,
    update_datetime        timestamp DEFAULT now()           NOT NULL,
    delete_datetime        timestamp,
    last_activity_datetime timestamp DEFAULT now()           NOT NULL,
    CONSTRAINT room_pkey PRIMARY KEY (id)
);

-- Добавление комментариев для таблицы core.room и её столбцов
COMMENT ON TABLE core.room IS 'Игровая комната';
COMMENT ON COLUMN core.room.id IS 'Уникальный идентификатор комнаты в формате UUID';
COMMENT ON COLUMN core.room.name IS 'Название комнаты. Значение по умолчанию — "Комната"';
COMMENT ON COLUMN core.room.description IS 'Описание комнаты';
COMMENT ON COLUMN core.room.owner_id IS 'Идентификатор владельца комнаты в формате UUID';
COMMENT ON COLUMN core.room.create_datetime IS 'Время создания записи. По умолчанию устанавливается на текущее время';
COMMENT ON COLUMN core.room.update_datetime IS 'Время последнего обновления записи. По умолчанию устанавливается на текущее время';
COMMENT ON COLUMN core.room.delete_datetime IS 'Время удаления записи, если она была удалена. Может быть NULL';
COMMENT ON COLUMN core.room.last_activity_datetime IS 'Время последней активности в комнате. По умолчанию устанавливается на текущее время';

-- Создание таблицы core.room_user
CREATE TABLE core.room_user
(
    user_id uuid  NOT NULL,
    room_id uuid  NOT NULL,
    roles   jsonb NOT NULL,
    PRIMARY KEY (user_id, room_id)
);

-- Добавление комментариев для таблицы core.room_user и её столбцов
COMMENT ON TABLE core.room_user IS 'Таблица связи пользователей и комнат';
COMMENT ON COLUMN core.room_user.user_id IS 'Уникальный идентификатор пользователя';
COMMENT ON COLUMN core.room_user.room_id IS 'Уникальный идентификатор комнаты';
COMMENT ON COLUMN core.room_user.roles IS 'Список ролей пользователя в комнате, например: [MASTER, PLAYER, MODERATOR]';

-- Создание индексов
CREATE INDEX room_owner_id ON core.room (owner_id);
CREATE INDEX room_user_room_id ON core.room_user (room_id, user_id);

-- Добавление внешних ключей
ALTER TABLE core.room_user
    ADD CONSTRAINT room_user_FK FOREIGN KEY (room_id) REFERENCES core.room (id);
ALTER TABLE core.room
    ADD CONSTRAINT room_owner_id_fkey FOREIGN KEY (owner_id) REFERENCES core.user (id) ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE core.room_user
    ADD CONSTRAINT FKroom_user134359 FOREIGN KEY (user_id) REFERENCES core.user (id);
