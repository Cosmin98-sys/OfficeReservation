CREATE TABLE workstations (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    floor       INTEGER NOT NULL,
    zone        VARCHAR(255) NOT NULL,
    type        VARCHAR(50) NOT NULL,
    capacity    INTEGER NOT NULL DEFAULT 1,
    description TEXT,
    is_active   BOOLEAN NOT NULL DEFAULT TRUE
);