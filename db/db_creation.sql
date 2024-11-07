CREATE TABLE Organisation (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
	username VARCHAAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	about VARCHAR(255) NOT NULL,
	phone VARCHAR(15) NOT NULL,
    name VARCHAR(255) NOT NULL,
    creation_date DATE,
    website VARCHAR(255)
);

CREATE TABLE Participant (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
	username VARCHAAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	about VARCHAR(255) NOT NULL,
	phone VARCHAR(15) NOT NULL,
	first_name VARCHAR(20) NOT NULL,
	last_name VARCHAR(20) NOT NULL,
    birthdate DATE NOT NULL
);

CREATE TABLE Opportunity (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(255) NOT NULL,
    type TEXT NOT NULL CHECK(type IN ('Erasmus', 'Internship', 'WorkAndTravel', 'Volunteer')),
    city VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2),
    no_participants INTEGER,
    organisation_id INTEGER,

    FOREIGN KEY (organisation_id) REFERENCES Organisation(id)
);

CREATE TABLE OpportunityPhotos ( 
	photo BLOB NOT NULL,
	organisation_id INTEGER,
	FOREIGN KEY (organisation_id) REFERENCES Organisation (id)
);