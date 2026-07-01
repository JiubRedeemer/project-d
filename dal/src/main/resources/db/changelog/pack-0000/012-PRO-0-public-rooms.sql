-- Добавляем флаг публичности к комнате
ALTER TABLE core.room
    ADD COLUMN is_public boolean NOT NULL DEFAULT false;

COMMENT ON COLUMN core.room.is_public IS 'Флаг публичности комнаты. Публичные комнаты видны всем пользователям в каталоге';

-- Таблица заявок на вступление в публичную комнату
CREATE TABLE core.room_join_request
(
    id               uuid      DEFAULT gen_random_uuid() NOT NULL,
    room_id          uuid                                NOT NULL,
    requester_id     uuid                                NOT NULL,
    status           text                                NOT NULL DEFAULT 'PENDING',
    create_datetime  timestamp DEFAULT now()             NOT NULL,
    CONSTRAINT room_join_request_pkey PRIMARY KEY (id),
    CONSTRAINT room_join_request_room_fk FOREIGN KEY (room_id) REFERENCES core.room (id) ON DELETE CASCADE,
    CONSTRAINT room_join_request_user_fk FOREIGN KEY (requester_id) REFERENCES core.user (id) ON DELETE CASCADE,
    CONSTRAINT room_join_request_status_check CHECK (status IN ('PENDING', 'ACCEPTED', 'DECLINED'))
);

CREATE INDEX room_join_request_room_id ON core.room_join_request (room_id);
CREATE INDEX room_join_request_requester_id ON core.room_join_request (requester_id);
CREATE UNIQUE INDEX room_join_request_unique ON core.room_join_request (room_id, requester_id) WHERE status = 'PENDING';

COMMENT ON TABLE core.room_join_request IS 'Заявки на вступление в публичные комнаты';
