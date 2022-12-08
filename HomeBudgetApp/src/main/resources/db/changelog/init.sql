--liquibase formatted sql

-- changeset VV:init-1
CREATE TABLE person(
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	username VARCHAR(64) NOT NULL UNIQUE,
	password VARCHAR(64) NOT NULL,
	role VARCHAR(1) NOT NULL DEFAULT 'U',
	created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP WITHOUT TIME ZONE,
	active BOOLEAN
);
-- changeset VV:init-2
CREATE TABLE currency(
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	currency_code SMALLINT NOT NULL UNIQUE
);
-- changeset VV:init-3
CREATE TABLE currency_rate(
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	rate_date DATE NOT NULL UNIQUE, 
	currency_id INTEGER NOT NULL REFERENCES currency(id),
	rate NUMERIC(14,4) NOT NULL,
	rate_scale INTEGER NOT NULL
);
-- changeset VV:init-4
CREATE TABLE account(
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	name VARCHAR(64) NOT NULL,
	description VARCHAR(100),
	person_id INTEGER NOT NULL REFERENCES person(id),
	currency_id INTEGER NOT NULL REFERENCES currency(id),
	currency_code SMALLINT NOT NULL,
	active BOOLEAN NOT NULL,
	include_in_total BOOLEAN NOT NULL,
	balance NUMERIC(18,2) NOT NULL
);
-- changeset VV:init-5
CREATE TABLE category(
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	name VARCHAR(64) NOT NULL,
	category_type VARCHAR(1) NOT NULL
);
-- changeset VV:init-6
CREATE TABLE partner(
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	name VARCHAR(64) NOT NULL
);
-- changeset VV:init-7
CREATE TABLE transaction(
	id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	transaction_type VARCHAR(1) NOT NULL,
	transaction_date DATE NOT NULL,
	acc1_id INTEGER NOT NULL REFERENCES account(id),
	acc2_id INTEGER REFERENCES account(id),
	amount NUMERIC(12,2) NOT NULL,
	partner_id INTEGER REFERENCES partner(id),
	comment VARCHAR(100),
	category_id INTEGER REFERENCES category(id)
);
-- changeset VV:init-8
INSERT INTO person(username, password, role, active) VALUES (
	'init', '$2a$10$24VZZQepM3a8TSKwvWvLWujsQ5Dj9VYtPdybf3oztBfqMEq5u1t1a', 'A', true);
-- changeset VV:init-9
INSERT INTO currency(currency_code) VALUES (933);
INSERT INTO currency(currency_code) VALUES (840);
INSERT INTO currency(currency_code) VALUES (978);
INSERT INTO currency(currency_code) VALUES (643);


