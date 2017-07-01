CREATE TABLE IF NOT EXISTS REGISTRATION(
	ID serial,
	givenName text,
	familyName text,
	address1 text,
	address2 text,
	city text,
	state text,
	zip text,
	country text,
	date timestamp DEFAULT NOW(),
	PRIMARY KEY(ID)
);
