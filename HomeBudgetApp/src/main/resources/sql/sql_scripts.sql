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
	include_in_total BOOLEAN NOT NULL,
	balance NUMERIC(18,2) NOT NULL
);

ALTER TABLE account ADD COLUMN balance NUMERIC(18,2) NOT NULL;

TRUNCATE TABLE account CASCADE;
TRUNCATE TABLE transaction;

INSERT INTO account(name, description, person_id, currency_id, currency_code, active, include_in_total)
	VALUES ('Наличные', 'Родной православный любимый наличман', 2, 2, 840, true, true);

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
	partner_id INT REFERENCES partner(id),
	comment VARCHAR(100),
	category_id INT REFERENCES category(id)
);

SELECT transaction_type AS type, SUM(amount) AS amount FROM transaction WHERE acc1_id=14 GROUP BY transaction_type;
SELECT SUM(amount) FROM transaction WHERE acc2_id=15;

SELECT (SUM(CASE WHEN acc1_id=15 AND transaction_type='I' THEN amount ELSE 0 END) + 
		SUM(CASE WHEN acc2_id=15 AND transaction_type='T' THEN amount ELSE 0 END) -
		SUM(CASE WHEN acc1_id=15 AND transaction_type='E' THEN amount ELSE 0 END) -
		SUM(CASE WHEN acc1_id=15 AND transaction_type='T' THEN amount ELSE 0 END)) AS amount FROM transaction;

ALTER TABLE transaction ALTER partner_id DROP NOT NULL,
					   ALTER category_id DROP NOT NULL;


INSERT INTO transaction(transaction_type, transaction_date, acc1_id, amount, partner_id, comment, category_id)
	VALUES ('E', '2022-11-15', 14, 100, 2, 'test expence transaction', 5);
	
INSERT INTO transaction(transaction_type, transaction_date, acc1_id, amount, partner_id, comment, category_id)
	VALUES ('E', '2022-11-15', 14, -100, 2, 'test expence transaction', 5);	
	
INSERT INTO transaction(transaction_type, transaction_date, acc1_id, amount, comment, category_id)
	VALUES ('I', '2022-11-10', 14, 1000, 'test income transaction', 8);
	
INSERT INTO transaction(transaction_type, transaction_date, acc1_id, acc2_id, amount, comment)
	VALUES ('T', '2022-11-10', 7, 4, 500, 'test transfer transaction');

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

DROP TRIGGER updated_table_person ON person;

DROP FUNCTION update_updated_at();
