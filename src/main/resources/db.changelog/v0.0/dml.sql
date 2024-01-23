--liquibase formatted sql
--changeset astolybko:1
INSERT INTO house (uuid, area, country, city, street, number, create_date)
VALUES (uuid_generate_v4(), '123m2', 'Belarus', 'Gomel', 'Sovetskaya', '25', now()),
       (uuid_generate_v4(), '234m2', 'Belarus', 'Brest', 'p.Pobedy', '11', now()),
       (uuid_generate_v4(), '234m2', 'Belarus', 'Minsk', 'p.Pobedy', '12', now()),
       (uuid_generate_v4(), '2034m2', 'Belarus', 'Gomel', 'p.Pobedy', '13', now()),
       (uuid_generate_v4(), '20034m2', 'Belarus', 'Gomel', 'p.Pobedy', '14', now());

INSERT INTO person (uuid, name, surname, sex, passport_series, passport_number, house_id, create_date, update_date)
VALUES (uuid_generate_v4(), 'TestName1', 'TestSurname1', 'MALE', 'SS', '1234', 1, now(), now()),
       (uuid_generate_v4(), 'TestName2', 'TestSurname2', 'FEMALE', 'SS', '1235', 1, now(), now()),
       (uuid_generate_v4(), 'TestName3', 'TestSurname3', 'MALE', 'SS', '1236', 1, now(), now()),
       (uuid_generate_v4(), 'TestName4', 'TestSurname4', 'FEMALE', 'SS', '1237', 2, now(), now()),
       (uuid_generate_v4(), 'TestName5', 'TestSurname5', 'MALE', 'SS', '1238', 2, now(), now()),
       (uuid_generate_v4(), 'TestName6', 'TestSurname6', 'FEMALE', 'SS', '1239', 3, now(), now()),
       (uuid_generate_v4(), 'TestName7', 'TestSurname7', 'MALE', 'SH', '1234', 3, now(), now()),
       (uuid_generate_v4(), 'TestName8', 'TestSurname8', 'FEMALE', 'SH', '1235', 4, now(), now()),
       (uuid_generate_v4(), 'TestName9', 'TestSurname9', 'MALE', 'SH', '1236', 4, now(), now()),
       (uuid_generate_v4(), 'TestName10', 'TestSurname10', 'FEMALE', 'SH', '1237', 4, now(), now());

INSERT INTO owner (person_id, house_id)
VALUES (1, 1),
       (4, 2),
       (5, 3),
       (7, 4),
       (1, 5),
       (10, 5);