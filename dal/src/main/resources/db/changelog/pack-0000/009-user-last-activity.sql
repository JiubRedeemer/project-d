ALTER TABLE core."user"
    ADD COLUMN last_activity timestamp;

UPDATE core."user"
SET last_activity = now()
WHERE last_activity IS NULL;

ALTER TABLE core."user"
    ALTER COLUMN last_activity SET NOT NULL;

COMMENT ON COLUMN core."user".last_activity IS 'Время последней активности пользователя';
