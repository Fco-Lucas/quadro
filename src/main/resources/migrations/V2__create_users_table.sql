CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'USER' CHECK (role IN ('USER', 'SUPERVISOR', 'ADMIN')),
    sector_id BIGINT NOT NULL REFERENCES sectors (id),
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'INACTIVE'))
);

CREATE INDEX idx_users_sector_id ON users (sector_id);
CREATE UNIQUE INDEX idx_users_cpf ON users (cpf);
