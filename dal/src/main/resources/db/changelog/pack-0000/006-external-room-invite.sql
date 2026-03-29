ALTER TABLE core.room_user_invite
    ALTER COLUMN invited_user_id DROP NOT NULL;

ALTER TABLE core.room_user_invite
    ADD COLUMN invited_email text;

ALTER TABLE core.room_user_invite
    ADD COLUMN invite_token text;

DROP INDEX IF EXISTS idx_unique_room_id_invited_user_id;

CREATE UNIQUE INDEX uq_room_user_invite_room_invited_user
    ON core.room_user_invite (room_id, invited_user_id)
    WHERE invited_user_id IS NOT NULL;

CREATE UNIQUE INDEX uq_room_user_invite_room_invited_email
    ON core.room_user_invite (room_id, lower(invited_email))
    WHERE invited_user_id IS NULL AND invited_email IS NOT NULL;

CREATE UNIQUE INDEX uq_room_user_invite_token
    ON core.room_user_invite (invite_token)
    WHERE invite_token IS NOT NULL;

COMMENT ON COLUMN core.room_user_invite.invited_email IS 'Email приглашённого до регистрации в системе';
COMMENT ON COLUMN core.room_user_invite.invite_token IS 'Токен одноразовой ссылки из письма';
