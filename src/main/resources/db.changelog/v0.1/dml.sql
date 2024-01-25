--liquibase formatted sql
--changeset astolybko:1
UPDATE person
SET house_id = 2
WHERE id = 1;

UPDATE person
SET house_id = 1
WHERE id = 4;

UPDATE person
SET house_id = 1
WHERE id = 1;

UPDATE person
SET house_id = 1
WHERE id = 5;

DELETE from owner
WHERE person_id = 1 AND house_id = 1;

DELETE from owner
WHERE person_id = 4 AND house_id = 2;

INSERT INTO owner(person_id, house_id)
VALUES (1, 2),
       (4, 1);