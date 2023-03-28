-- Droping hotel chains will cascade and delete hotel as well as their rooms
-- Rooms will get automatically archived once deleted, so to clear the database they also need to get deleted
DROP TABLE IF EXISTS HotelChain CASCADE;
DROP TABLE IF EXISTS Hotel CASCADE;
DROP TABLE IF EXISTS Room CASCADE;
DROP TABLE IF EXISTS RoomArchive CASCADE;
DROP TABLE IF EXISTS Person CASCADE;
DROP TABLE IF EXISTS Account CASCADE;
DROP TABLE IF EXISTS CustomerAccount CASCADE;
DROP TABLE IF EXISTS EmployeeAccount CASCADE;
DROP TABLE IF EXISTS Booking CASCADE;
DROP FUNCTION IF EXISTS archiveRoom;

-- Basis for all persons
CREATE TABLE Person (
	person_id SERIAL PRIMARY KEY,
	first_name VARCHAR(128) NOT NULL,
	last_name VARCHAR(128) NOT NULL,
	address VARCHAR(256) NOT NULL
);

CREATE TABLE Account (
	account_id SERIAL PRIMARY KEY,
	person_id INT,
	ssn_sin VARCHAR(32) UNIQUE NOT NULL,
	creation_date DATE NOT NULL,
	
	-- Change this later with the proper type and size
	salt CHAR(16) NOT NULL,
	hashed_salted_password CHAR(64) NOT NULL,
	
	CONSTRAINT fk_person_id
		FOREIGN KEY(person_id) 
		REFERENCES Person(person_id) 
		ON DELETE CASCADE
);

CREATE TABLE CustomerAccount (
	customer_id SERIAL PRIMARY KEY,
	account_id INT,
	
	CONSTRAINT fk_account_id
		FOREIGN KEY(account_id) 
		REFERENCES Account(account_id) 
		ON DELETE CASCADE
);

CREATE TABLE EmployeeAccount (
	employee_id SERIAL PRIMARY KEY,
	account_id INT,
	
	perm_create_booking boolean,
	perm_hotel_manager boolean,
	perm_chain_manager boolean,
	
	CONSTRAINT fk_account_id
		FOREIGN KEY(account_id) 
		REFERENCES Account(account_id) 
		ON DELETE CASCADE
);

-- Once deleted, the deletion will cascade down to hotels then rooms
CREATE TABLE HotelChain(
	chain_id SERIAL PRIMARY KEY,
	chain_name VARCHAR(64) NOT NULL,
	address_central_office VARCHAR(256) NOT NULL,
	email VARCHAR(128) NOT NULL,
	phone VARCHAR(128) NOT NULL
);

-- Depends on hotel chain, will get deleted if that one gets deleted too.
CREATE TABLE Hotel(
	hotel_id SERIAL PRIMARY KEY,
	chain_id INT,
	manager_id INT, -- We were told to make this not null but what happens to hotel once manager is deleted?
	address VARCHAR(256) NOT NULL,
	phone VARCHAR(128) NOT NULL,
	rating NUMERIC(2,1) NOT NULL,
	room_count INT NOT NULL,
	
	CHECK(room_count >= 0),
	CHECK(rating >= 0 AND rating <= 5),
	CONSTRAINT fk_chain_id
		FOREIGN KEY(chain_id) 
		REFERENCES HotelChain(chain_id) 
		ON DELETE CASCADE,
	CONSTRAINT fk_manager_id
		FOREIGN KEY(manager_id) 
		REFERENCES EmployeeAccount(employee_id) 
		ON DELETE SET NULL
);

-- Rooms get archived once deleted so booking still point to a room that exists
-- Room ID is the id considering ALL rooms, and room number is on a per-hotel basis
-- Tags is a flag enum where each character represents one amenity.
   -- Char[0] = TV
   -- Char[1] = Air conditionning
   -- Char[2] = Microwave
   -- Char[3] = Fridge
   -- Char[4] = Smoking allowed
   -- Char[5] = Pets friendly
   -- Char[6] = Sea view
   -- Char[7] = Mountain view
   -- ... More to be added up to 32
CREATE TABLE Room(
	room_id SERIAL PRIMARY KEY,
	room_number INT UNIQUE NOT NULL,
	hotel_id INT,
	price_per_night NUMERIC(8,2) NOT NULL,
	room_capacity INT NOT NULL,
	extension_capacity INT NOT NULL,
	tags CHAR(32) NOT NULL,
	notes text,
	
	CHECK(room_capacity >= 1),
	CHECK(extension_capacity >= 0),
	CONSTRAINT fk_hotel_id
		FOREIGN KEY(hotel_id) 
		REFERENCES Hotel(hotel_id) 
		ON DELETE CASCADE
);

-- Basically a copy of Room without constraints
CREATE TABLE RoomArchive(
	room_id INT PRIMARY KEY,
	room_number INT,
	hotel_id INT,
	price_per_night NUMERIC(8,2) NOT NULL,
	room_capacity INT NOT NULL,
	extension_capacity INT NOT NULL,
	tags CHAR(32) NOT NULL,
	notes text
);

-- Automatically archive rooms as they get deleted
CREATE FUNCTION archiveRoom() RETURNS trigger AS $$
BEGIN
	INSERT INTO RoomArchive(room_id, room_number, hotel_id, price_per_night, room_capacity, extension_capacity, tags, notes)
	VALUES (OLD.room_id, OLD.room_number, OLD.hotel_id, OLD.price_per_night, OLD.room_capacity, OLD.extension_capacity, OLD.tags, OLD.notes);
	RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER archiveRoom
BEFORE DELETE ON Room FOR EACH ROW
EXECUTE PROCEDURE archiveRoom();

-- Room ID isn't checked. The foreign key might be pointing to a room or archived room.
-- Either way the ID SHOULD be valid.
-- Once a customer is deleted, their ID goes null
CREATE TABLE Booking (
	booking_id SERIAL PRIMARY KEY,
	room_id INT,
	person_id INT,
	start_date DATE NOT NULL,
	end_date DATE NOT NULL,
	
	CONSTRAINT fk_person_id
		FOREIGN KEY(person_id) 
		REFERENCES Person(person_id) 
		ON DELETE SET NULL
);


-- This is our playground
INSERT INTO HotelChain(chain_name, address_central_office, email, phone)
VALUES
('Confort Inn','4348  Bank St, ON, Ottawa, Canada','contact@confortinn.ca','613-555-5555'),
('Blue Hotel','2507  Montreal Road, ON, Ottawa, Canada','contact@bluehotel.ca','613-333-3333');

INSERT INTO Hotel(chain_id, address, phone, rating, room_count)
VALUES
(1, '2589  Thurston Dr, ON, Ottawa, Canada', '613-555-0001', 2.0, 0),
(1, '1277  MacLaren Street, ON, Ottawa, Canada', '613-555-0002', 3.0, 0),
(2, '1981 Carling Avenue, ON, Ottawa, Canada', '613-555-0003', 4.0, 0),
(2, '2447  MacLaren Street, ON, Ottawa, Canada', '613-555-0004', 5.0, 0);

INSERT INTO Room(room_number, hotel_id, price_per_night, room_capacity, extension_capacity, tags, notes)
VALUES
(101, 1, 96.00, 1, 0, 'XX000000', NULL),
(102, 1, 132.00, 2, 0, 'XX00000X', NULL),
(103, 2, 84.00, 1, 0, 'XXX00000', NULL),
(104, 2, 110.00, 2, 0, 'XXX0000X', NULL),
(105, 3, 104.99, 1, 1, 'XX0000X0', NULL),
(106, 3, 230.99, 2, 1, 'XX00000X', NULL),
(107, 4, 43.23, 1, 2, '0XX000X0', 'The faucets water is brown'),
(108, 4, 92.75, 2, 0, '0XX0000X', NULL);

INSERT INTO Person(first_name, last_name, address)
VALUES
('Matthew', 'Kirkpatrick', '1161 Indiana Avenue, Wahiawa, HI, USA'),
('Betty', 'Devane', '4476 Vernon Street, San Diego, CA, USA'),
('Gertrude','Shover', '857 Hilltop Drive, Amarillo, TX, USA'),
('Maddison','Vaclavik', '1615 Charleton Ave, Hamilton, ON, Canada'),
('Joe','Mama','2125 Goyeau Ave, Widsor, ON, Canada'),
('Franco','St Angelo','3889 rue de la Gauchetière, Montreal, QC, Canada');

INSERT INTO Account(person_id, ssn_sin, creation_date, salt, hashed_salted_password)
VALUES
(1, '991-313-305', NOW(), 'cGCInaZBjehzxmfr', '4fbba91c8268a063531d82ccf60d07db283d56cb10c5120ffd38e4cc3e67dfec'), --joebiden.26 --THE SALT IS PREPENDED
(2, '608-260-188', NOW(), 'E5mdUqwV6jBXU5C8', 'ddd20a1f75dac15398a6ef0620ea1fc0b6c7c25764690c523fd0d539cb07a88c'), --ILOVECAts%635
(3, '382-446-623', NOW(), 'ThnvdJACYIqCozDD', 'f129c5e1628ec5915f6b65d78e5697ca5481fd1be140edac6b71bb5f1d292076'), --crab-enthausiast27
(4, '941-463-556', NOW(), 'zIk6z9xHmlxmY71H', 'f1fdfb3e761c1cd27c137f0575e2d2c98739b321f00ac807452197ac8dd4bd41'), --abc123!
(5, '263-134-066', NOW(), 'gD5YJ9brZ4qRjQ3w', 'e43adca44d1aaba45c8fd899a462ab9f7af59df443fb0364e02ed6c3ab26b1a6'), --onemillion=1000000
(6, '925-092-975', NOW(), 'mYRNkTwO2cDCdBME', '56c483835403ccd8b47be03f1ec003de0d99ad4c240ca815e21a4b5cfb4d8a1a'); --davisbigPASSWORD5

INSERT INTO CustomerAccount(account_id) VALUES(1),(2);

INSERT INTO EmployeeAccount(account_id, perm_create_booking, perm_hotel_manager, perm_chain_manager)
VALUES
(3, TRUE, FALSE, FALSE),
(4, TRUE, TRUE, TRUE),
(5, TRUE, TRUE, FALSE),
(6, TRUE, TRUE, FALSE);

UPDATE Hotel SET manager_id = 3 WHERE hotel_id = 1;
UPDATE Hotel SET manager_id = 2 WHERE hotel_id = 2;
UPDATE Hotel SET manager_id = 3 WHERE hotel_id = 3;
UPDATE Hotel SET manager_id = 4 WHERE hotel_id = 4;

--DELETE FROM HotelChain WHERE chain_name = 'Confort Inn';
--DELETE FROM Room;
--SELECT * FROM RoomArchive;
--SELECT * FROM Account;
