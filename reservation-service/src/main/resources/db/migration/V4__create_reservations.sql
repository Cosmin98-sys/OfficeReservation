CREATE TABLE reservations
(
    id             BIGSERIAL PRIMARY KEY,
    user_id        BIGINT      NOT NULL REFERENCES users (id),
    workstation_id BIGINT      NOT NULL REFERENCES workstations (id),
    date           DATE        NOT NULL,
    status         VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    created_at     TIMESTAMP   NOT NULL DEFAULT NOW()
);
