CREATE TABLE core.room_user_invites
(
    room_id         uuid                    NOT NULL,
    owner_id        uuid                    NOT NULL,
    invited_user_id uuid                    NOT NULL,
    create_datetime timestamp DEFAULT now() NOT NULL,
    role            text                    NOT NULL,
    status          text                    NOT NULL,
    PRIMARY KEY (room_id, owner_id, invited_user_id)
);

-- Добавление внешних ключей
ALTER TABLE core.room_user_invites
    ADD CONSTRAINT invite_room_FK FOREIGN KEY (room_id) REFERENCES core.rooms (id);
ALTER TABLE core.room_user_invites
    ADD CONSTRAINT invite_owner_FK FOREIGN KEY (owner_id) REFERENCES core.users (id);
ALTER TABLE core.room_user_invites
    ADD CONSTRAINT invite_invited_user_FK FOREIGN KEY (invited_user_id) REFERENCES core.users (id);

-- Создание индексов
CREATE INDEX idx_room_id ON core.room_user_invites (room_id);
CREATE INDEX idx_owner_id ON core.room_user_invites (owner_id);
CREATE INDEX idx_invited_user_id ON core.room_user_invites (invited_user_id);
CREATE UNIQUE INDEX idx_invited_user_id ON core.room_user_invites (room_id, invited_user_id);

-- Добавление комментариев
COMMENT ON TABLE core.room_user_invites IS 'Таблица приглашений пользователей в комнаты';
COMMENT ON COLUMN core.room_user_invites.room_id IS 'Идентификатор комнаты';
COMMENT ON COLUMN core.room_user_invites.owner_id IS 'Идентификатор владельца комнаты';
COMMENT ON COLUMN core.room_user_invites.invited_user_id IS 'Идентификатор приглашенного пользователя';
COMMENT ON COLUMN core.room_user_invites.create_datetime IS 'Дата создания приглашения';
COMMENT ON COLUMN core.room_user_invites.role IS 'Предлагаемая роль';
COMMENT ON COLUMN core.room_user_invites.status IS 'Статус приглашения (например, принято, отклонено)';
