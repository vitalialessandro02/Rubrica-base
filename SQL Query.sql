CREATE DATABASE IF NOT EXISTS rubrica;

USE rubrica;
CREATE TABLE IF NOT EXISTS persone (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    nome        VARCHAR(100) NOT NULL,
    cognome     VARCHAR(100) NOT NULL,
    indirizzo   VARCHAR(255) NOT NULL DEFAULT '',
    telefono    VARCHAR(30)  NOT NULL DEFAULT '',
    eta         INT          NOT NULL
);

-- Tabella degli utenti (la password è salvata come hash SHA-256 in esadecimale)
CREATE TABLE IF NOT EXISTS utenti (
    username       VARCHAR(50) PRIMARY KEY,
    password_hash  CHAR(64)    NOT NULL
);