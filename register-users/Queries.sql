CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    nyap VARCHAR(255) NOT NULL,  -- Nombre y Apellido
    dni VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    password VARCHAR(255) NOT NULL,
    cvu VARCHAR(22) NOT NULL UNIQUE,
    alias VARCHAR(50) UNIQUE
);
