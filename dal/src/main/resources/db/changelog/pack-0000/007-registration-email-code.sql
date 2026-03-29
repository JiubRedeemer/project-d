CREATE TABLE core.registration_email_code
(
    email       text                    NOT NULL,
    code_hash   text                    NOT NULL,
    expires_at  timestamp               NOT NULL,
    created_at  timestamp DEFAULT now() NOT NULL,
    attempts    integer   DEFAULT 0     NOT NULL,
    PRIMARY KEY (email)
);

COMMENT ON TABLE core.registration_email_code IS 'Одноразовые коды подтверждения email при регистрации';
COMMENT ON COLUMN core.registration_email_code.email IS 'Нормализованный email (lower(trim))';
COMMENT ON COLUMN core.registration_email_code.code_hash IS 'BCrypt-хэш кода';
COMMENT ON COLUMN core.registration_email_code.expires_at IS 'Срок действия кода';
COMMENT ON COLUMN core.registration_email_code.created_at IS 'Время отправки (cooldown)';
COMMENT ON COLUMN core.registration_email_code.attempts IS 'Число неверных попыток ввода';
