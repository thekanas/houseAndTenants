--liquibase formatted sql
--changeset astolybko:1
CREATE TABLE IF NOT EXISTS house
(
    id          BIGSERIAL PRIMARY KEY,
    uuid        UUID         NOT NULL,
    area        VARCHAR(128),
    country     VARCHAR(50)  NOT NULL,
    city        VARCHAR(50)  NOT NULL,
    street      VARCHAR(128) NOT NULL,
    number      VARCHAR(10)  NOT NULL,
    create_date TIMESTAMP

);

CREATE INDEX house_uuid
    ON house (uuid);

CREATE TABLE IF NOT EXISTS person
(
    id              BIGSERIAL PRIMARY KEY,
    uuid            UUID         NOT NULL,
    name            VARCHAR(128) NOT NULL,
    surname         VARCHAR(128) NOT NULL,
    sex             VARCHAR(20),
    passport_series VARCHAR(20),
    passport_number VARCHAR(20),
    house_id        BIGINT REFERENCES house (id),
    create_date     TIMESTAMP,
    update_date     TIMESTAMP,
    UNIQUE (passport_series, passport_number)

);

CREATE INDEX person_uuid
    ON person (uuid);

CREATE TABLE IF NOT EXISTS owner
(
    person_id BIGINT REFERENCES person (id) ON DELETE CASCADE ,
    house_id  BIGINT REFERENCES house (id) ON DELETE CASCADE ,
    PRIMARY KEY (person_id, house_id)

);

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";