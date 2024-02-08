DELETE FROM house_history;
DELETE FROM person;
DELETE FROM house;

ALTER SEQUENCE person_id_seq RESTART;
ALTER SEQUENCE house_id_seq RESTART;
ALTER SEQUENCE house_history_id_seq RESTART;

INSERT INTO house (uuid, area, country, city, street, number, create_date)
VALUES ('0b84f914-1308-4eb2-acc0-b8e362917f22'::uuid, '1m2', 'Belarus', 'Gomel', 'Sovetskaya', '1', '2024-01-24 18:00:00.0'),
       ('973223de-94dc-4f6a-ad4a-ab0bb8ca34c0'::uuid, '2m2', 'Belarus', 'Gomel', 'Sovetskaya', '2', '2024-01-24 18:00:00.0');

INSERT INTO person (uuid, name, surname, sex, passport_series, passport_number, house_id, create_date, update_date)
VALUES ('dc02ac84-3518-4839-aa4f-046ba89b9688'::uuid, 'TestName1', 'TestSurname1', 'MALE', 'SS', '1234', 1, '2024-01-24 18:00:00.0', '2024-01-24 18:00:00.0'),
       ('7d2d01ac-c1f2-4505-bf0e-457d16c882d5'::uuid, 'TestName2', 'TestSurname2', 'FEMALE', 'SS', '1235', 1, '2024-01-24 18:00:00.0', '2024-01-24 18:00:00.0'),
       ('36bd9573-2c3d-46f2-b6c8-323509a6520c'::uuid, 'TestName3', 'TestSurname3', 'MALE', 'SS', '1236', 1, '2024-01-24 18:00:00.0', '2024-01-24 18:00:00.0');



/*UPDATE person
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
       (4, 1);*/