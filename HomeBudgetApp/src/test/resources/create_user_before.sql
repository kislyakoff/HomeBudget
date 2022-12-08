DELETE FROM person;

INSERT INTO person(username, password, role, active) VALUES (
	'test', '$2a$10$B6VV451/xxhZVx6O.gPaiuGNIiYFh8HgU9stbJ8bKGBBJ4ftA6yEK', 'A', true);
INSERT INTO person(username, password, role, active) VALUES (
	'test2', '$2a$10$DUbEo/WHzovD.WKoPbvR5.pkuQBbsXzwUKDsmZMmKVyxNBZmAYs76', 'U', true);