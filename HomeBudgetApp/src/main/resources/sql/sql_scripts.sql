CREATE TABLE person(
	id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	username VARCHAR(64) NOT NULL UNIQUE,
	password VARCHAR(64) NOT NULL,
	role VARCHAR(1) NOT NULL DEFAULT 'U',
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP	
);

ALTER TABLE person ADD COLUMN active BOOLEAN;

ALTER TABLE person DROP CONSTRAINT person_password_key;

CREATE TABLE currency(
	id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	currency_code SMALLINT NOT NULL UNIQUE
);

CREATE TABLE currency_rate(
	id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	rate_date DATE NOT NULL UNIQUE, 
	currency_id INT NOT NULL REFERENCES currency(id),
	rate NUMERIC(14,4) NOT NULL,
	rate_scale INT NOT NULL
);

CREATE TABLE account(
	id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	name VARCHAR(64) NOT NULL,
	description VARCHAR(100),
	person_id INT NOT NULL REFERENCES person(id),
	currency_id INT NOT NULL REFERENCES currency(id),
	currency_code SMALLINT NOT NULL,
	active BOOLEAN NOT NULL,
	include_in_total BOOLEAN NOT NULL
);

CREATE TABLE category(
	id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	name VARCHAR(64) NOT NULL,
	category_type VARCHAR(1) NOT NULL
);

CREATE TABLE partner(
	id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	name VARCHAR(64) NOT NULL
);

CREATE TABLE transaction(
	id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	transaction_type VARCHAR(1) NOT NULL,
	transaction_date DATE NOT NULL,
	acc1_id INT NOT NULL REFERENCES account(id),
	acc2_id INT REFERENCES account(id),
	amount NUMERIC(12,2) NOT NULL,
	partner_id INT NOT NULL REFERENCES partner(id),
	comment VARCHAR(100),
	category_id INT NOT NULL REFERENCES category(id)
);


INSERT INTO person(username, password, role) 
	VALUES ('kislyakoff', '$2a$10$2EUleE/5eESoYova3P4eo.nG.OxP2ZP3cee2oBfEA31mDgw3cOWkC', 'A');
INSERT INTO person(username, password) 
	VALUES ('test', '$2a$10$lIZ5vUQdSZVdN./D2nldG.eh4rWSLaLh4P642J7uXaIOqZTWby7s6');
	
INSERT INTO currency(currency_code) VALUES (933);
INSERT INTO currency(currency_code) VALUES (840);
INSERT INTO currency(currency_code) VALUES (978);
INSERT INTO currency(currency_code) VALUES (643);

-- функция для триггера автообновления колонки updated_at
CREATE OR REPLACE FUNCTION update_updated_at()
    RETURNS TRIGGER AS $$
    BEGIN
    NEW.updated_at = now();
    RETURN NEW;
    END;
$$ language 'plpgsql';

-- применение триггера к таблице person
CREATE TRIGGER updated_table_person
    BEFORE UPDATE
    ON 
        person
    FOR EACH ROW
EXECUTE PROCEDURE update_updated_at();