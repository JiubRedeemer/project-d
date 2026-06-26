CREATE TABLE IF NOT EXISTS core.combat_session
(
    id                 UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    room_id            UUID        NOT NULL REFERENCES core.room (id) ON DELETE CASCADE,
    state              VARCHAR(20) NOT NULL DEFAULT 'PREPARING',
    round              INT         NOT NULL DEFAULT 1,
    current_turn_index INT         NOT NULL DEFAULT 0,
    created_at         TIMESTAMP        NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS core.combat_participant
(
    id               UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    session_id       UUID        NOT NULL REFERENCES core.combat_session (id) ON DELETE CASCADE,
    participant_type VARCHAR(20) NOT NULL DEFAULT 'CHARACTER',
    reference_id     UUID,
    display_name     VARCHAR(255),
    initiative       INT,
    is_ready         BOOLEAN     NOT NULL DEFAULT false,
    copy_index       INT         NOT NULL DEFAULT 1,
    sort_order       INT,
    current_hp       INT,
    max_hp           INT
);
