CREATE TABLE core.room_schedule
(
    id               uuid      DEFAULT gen_random_uuid() NOT NULL,
    room_id          uuid                                NOT NULL,
    is_recurring     boolean   NOT NULL DEFAULT false,
    session_datetime timestamp,
    recurrence_type  text,
    day_of_week      smallint,
    day_of_month     smallint,
    session_time     time,
    CONSTRAINT room_schedule_pkey PRIMARY KEY (id),
    CONSTRAINT room_schedule_room_fk FOREIGN KEY (room_id) REFERENCES core.room (id) ON DELETE CASCADE,
    CONSTRAINT room_schedule_room_unique UNIQUE (room_id),
    CONSTRAINT room_schedule_recurrence_check CHECK (recurrence_type IN ('WEEKLY', 'BIWEEKLY', 'MONTHLY'))
);

COMMENT ON TABLE core.room_schedule IS 'Расписание игровых сессий для комнат';
