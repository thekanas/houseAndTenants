--liquibase formatted sql
--changeset astolybko:1 splitStatements:false

CREATE TYPE type_person AS ENUM
    ('OWNER', 'TENANT');

CREATE TABLE IF NOT EXISTS house_history
(
    id        BIGSERIAL PRIMARY KEY,
    house_id  BIGINT NOT NULL,
    person_id BIGINT NOT NULL,
    date      TIMESTAMP,
    type      type_person
);

CREATE OR REPLACE FUNCTION history_insert_tenant()
    RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.house_id IS DISTINCT FROM OLD.house_id THEN
        INSERT INTO house_history(house_id, person_id, date, type)
        VALUES (OLD.house_id, OLD.id, now(), 'TENANT');
    END IF;
    RETURN NEW;
END;
$$
    LANGUAGE 'plpgsql';

CREATE TRIGGER history_tenant
    AFTER UPDATE OF house_id
    ON person
    FOR EACH ROW
EXECUTE FUNCTION history_insert_tenant();

CREATE OR REPLACE FUNCTION history_insert_owner()
    RETURNS TRIGGER AS
$$
BEGIN
    INSERT INTO house_history(house_id, person_id, date, type)
    VALUES (OLD.house_id, OLD.person_id, now(), 'OWNER');
    RETURN NEW;
END;
$$
    LANGUAGE 'plpgsql';

CREATE TRIGGER history_owner
    AFTER DELETE
    ON owner
    FOR EACH ROW
EXECUTE FUNCTION history_insert_owner();