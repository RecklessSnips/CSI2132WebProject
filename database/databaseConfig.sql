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
DROP TABLE IF EXISTS Renting CASCADE;
DROP FUNCTION IF EXISTS archiveRoom;
DROP TRIGGER IF EXISTS copy_booking_to_renting_trigger ON Renting;

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
	username VARCHAR(32) UNIQUE NOT NULL,
	ssn_sin VARCHAR(32) UNIQUE NOT NULL,
	creation_date DATE NOT NULL,
	
	account_type INT NOT NULL,
	
	-- Change this later with the proper type and size
	salt CHAR(16) NOT NULL,
	hashed_salted_password CHAR(64) NOT NULL,
	
	CONSTRAINT fk_person_id
		FOREIGN KEY(person_id) 
		REFERENCES Person(person_id) 
		ON DELETE CASCADE
);

CREATE INDEX username_index ON Account(username);

-- Once deleted, the deletion will cascade down to hotels then rooms
CREATE TABLE HotelChain(
	chain_id SERIAL PRIMARY KEY,
	chain_name VARCHAR(64) NOT NULL,
	address_central_office VARCHAR(256) NOT NULL,
	email VARCHAR(128) NOT NULL,
	chain_phone VARCHAR(128) NOT NULL
);

-- Depends on hotel chain, will get deleted if that one gets deleted too.
CREATE TABLE Hotel(
	hotel_id SERIAL PRIMARY KEY,
	chain_id INT,
	manager_id INT, -- We were told to make this not null but what happens to hotel once manager is deleted?
	address VARCHAR(256) NOT NULL,
	area VARCHAR(128) NOT NULL,
	hotel_phone VARCHAR(128) NOT NULL,
	rating NUMERIC(2,1) NOT NULL,
	category INT NOT NULL,
	
	CHECK(rating >= 0 AND rating <= 5),
	CONSTRAINT fk_chain_id
		FOREIGN KEY(chain_id) 
		REFERENCES HotelChain(chain_id) 
		ON DELETE CASCADE,
	CONSTRAINT fk_manager_id
		FOREIGN KEY(manager_id) 
		REFERENCES Account(account_id)
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
	room_number INT NOT NULL,
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
CREATE OR REPLACE FUNCTION archive_room() RETURNS trigger AS $$
BEGIN
	INSERT INTO RoomArchive(room_id, room_number, hotel_id, price_per_night, room_capacity, extension_capacity, tags, notes)
	VALUES (OLD.room_id, OLD.room_number, OLD.hotel_id, OLD.price_per_night, OLD.room_capacity, OLD.extension_capacity, OLD.tags, OLD.notes);
	RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER archive_room
BEFORE DELETE ON Room FOR EACH ROW
EXECUTE PROCEDURE archive_room();

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

CREATE TABLE Renting (LIKE Booking INCLUDING ALL);


CREATE OR REPLACE FUNCTION check_booking_overlap()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM Booking
        WHERE room_id = NEW.room_id
        AND NOT (end_date <= NEW.start_date OR start_date >= NEW.end_date)
    ) THEN
        RAISE EXCEPTION 'Booking overlaps with existing bookings for this room';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER booking_overlap_check
BEFORE INSERT ON Booking
FOR EACH ROW
EXECUTE FUNCTION check_booking_overlap();

-- Automatically archive bookings to rentings
CREATE OR REPLACE FUNCTION copy_booking_to_renting() RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO Renting SELECT * FROM Booking WHERE Booking.booking_id = NEW.booking_id;
    DELETE FROM Booking WHERE Booking.id = NEW.booking_id;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER copy_booking_to_renting_trigger
    AFTER INSERT ON Renting
    FOR EACH ROW
    EXECUTE FUNCTION copy_booking_to_renting();

-- There is going to be a lot of bookings.
-- We need to find the booking of a person fast, and the booking of a room fast.
CREATE INDEX booking_person_index ON Booking(person_id);
CREATE INDEX booking_room_index ON Booking(room_id);


-- This is our playground
INSERT INTO HotelChain(chain_name, address_central_office, email, chain_phone)
VALUES
('Confort Inn', '4348 Bank St, ON, Ottawa, Canada', 'contact@confortinn.ca', '613-555-5555'),
('Blue Hotel', '2507 Montreal Road, ON, Ottawa, Canada', 'contact@bluehotel.ca', '613-333-3333'),
('Grand Hotel', '123 Main St, NY, New York, USA', 'contact@grandhotel.com', '212-555-5555'),
('Royal Palace', '16 Rue de la Paix, Paris, France', 'contact@royalpalace.fr', '+33 1 23 45 67 89'),
('Sunset Resort', '1234 Beach Blvd, CA, Los Angeles, USA', 'contact@sunsetresort.com', '310-555-5555');

INSERT INTO Person(first_name, last_name, address)
VALUES
('Matthew', 'Kirkpatrick', '1161 Indiana Avenue, Wahiawa, HI, USA'),
('Betty', 'Devane', '4476 Vernon Street, San Diego, CA, USA'),
('Gertrude','Shover', '857 Hilltop Drive, Amarillo, TX, USA'),
('Maddison','Vaclavik', '1615 Charleton Ave, Hamilton, ON, Canada'),
('Joe','Mama','2125 Goyeau Ave, Widsor, ON, Canada'),
('Franco','St Angelo','3889 rue de la Gauchetière, Montreal, QC, Canada');

INSERT INTO Account(person_id, account_type, username, ssn_sin, creation_date, salt, hashed_salted_password)
VALUES
(1, 0, 'matkirk', '991-313-305', NOW(), 'cGCInaZBjehzxmfr', '2042628a67e6d98deff79d1a3b59069faac25af8ea3865251dd05d12c367557a'), --joebiden.26 --The salt is suffix, username prefix
(2, 0, 'BettyDev92', '608-260-188', NOW(), 'E5mdUqwV6jBXU5C8', '022217307ccbe464c7c28d898e4b89b76f9e7cb45ba175ce7c1b3649482b387f'), --ILOVECAts%635
(3, 1, 'Sho_sho', '382-446-623', NOW(), 'ThnvdJACYIqCozDD', '6c4f19727fdcf837fcb8491d8349efe33c37457cedcd94b3c612b33147173a2b'), --crab-enthausiast27
(4, 1, 'MaddisonVaclavik', '941-463-556', NOW(), 'zIk6z9xHmlxmY71H', 'd4542fe96af265106088f11b5065dfa6f47ddde9070dcdbacf479c82d4acae15'), --abc123!
(5, 1, 'Mama', '263-134-066', NOW(), 'gD5YJ9brZ4qRjQ3w', '3b189ab7034b331b4a96ee8f279514c056706b9cdbddb09b6ffb7b667a03e128'), --onemillion=1000000
(6, 1, 'st__angl', '925-092-975', NOW(), 'mYRNkTwO2cDCdBME', 'ff10074d83e75702cffa3adc6270604f8232f122786e4cbec222bbae5dad96e9'); --davisbigPASSWORD5

INSERT INTO Hotel(chain_id, manager_id, category, address, area, hotel_phone, rating)
VALUES
(1, 3, 0, '123 Main St, ON, Ottawa, Canada', 'Ottawa', '613-111-1111', 3.5),
(1, 4, 1, '456 Queen St, ON, Ottawa, Canada', 'Ottawa', '613-111-1112', 4.0),
(1, 5, 1, '789 King St, ON, Ottawa, Canada', 'Ottawa', '613-111-1113', 4.5),
(1, 6, 2, '100 Elgin St, ON, Ottawa, Canada', 'Ottawa', '613-111-1114', 3.0),
(1, 4, 2, '200 Rideau St, ON, Ottawa, Canada', 'Ottawa', '613-111-1115', 2.5),
(1, 5, 3, '300 Sparks St, ON, Ottawa, Canada', 'Ottawa', '613-111-1116', 3.0),
(1, 6, 3, '400 Laurier Ave, ON, Ottawa, Canada', 'Ottawa', '613-111-1117', 4.5),
(1, 3, 4, '500 Sussex Dr, ON, Ottawa, Canada', 'Ottawa', '613-111-1118', 5.0),
(2, 5, 0, '100 Rue St-Jacques, QC, Montreal, Canada', 'Montreal', '514-222-2221', 3.0),
(2, 5, 1, '200 Rue St-Antoine, QC, Montreal, Canada', 'Montreal', '514-222-2222', 4.5),
(2, 6, 1, '300 Rue Peel, QC, Montreal, Canada', 'Montreal', '514-222-2223', 4.0),
(2, 6, 2, '400 Blvd Rene-Levesque, QC, Montreal, Canada', 'Montreal', '514-222-2224', 3.5),
(2, 3, 2, '500 Rue Sherbrooke O, QC, Montreal, Canada', 'Montreal', '514-222-2225', 2.5),
(2, 3, 3, '600 Rue St-Denis, QC, Montreal, Canada', 'Montreal', '514-222-2226', 3.0),
(2, 3, 3, '700 Rue Guy, QC, Montreal, Canada', 'Montreal', '514-222-2227', 4.5),
(2, 4, 4, '800 Rue University, QC, Montreal, Canada', 'Montreal', '514-222-2228', 5.0),
(3, 4, 1, '303 Madison Ave, NY, New York, USA', 'New York', '+1-212-333-3332', 4.5),
(3, 5, 2, '404 Park Ave, NY, New York, USA', 'New York', '+1-212-333-3333', 3.0),
(3, 5, 2, '505 Lexington Ave, NY, New York, USA', 'New York', '+1-212-333-3334', 2.5),
(3, 6, 3, '606 7th Ave, NY, New York, USA', 'New York', '+1-212-333-3335', 3.5),
(3, 6, 3, '707 8th Ave, NY, New York, USA', 'New York', '+1-212-333-3336', 4.0),
(3, 3, 4, '808 Broadway, NY, New York, USA', 'New York', '+1-212-333-3337', 4.5),
(3, 4, 4, '909 5th Ave, NY, New York, USA', 'New York', '+1-212-333-3338', 5.0),
(3, 5, 4, '1010 Park Ave, NY, New York, USA', 'New York', '+1-212-333-3339', 4.0),
(4, 6, 0, '123 Main St, CA, Los Angeles, USA', 'Los Angeles', '+1-310-555-5552', 2.5),
(4, 3, 1, '456 Vine St, CA, Los Angeles, USA', 'Los Angeles', '+1-310-555-5553', 3.0),
(4, 4, 1, '789 Hollywood Blvd, CA, Los Angeles, USA', 'Los Angeles', '+1-310-555-5554', 3.5),
(4, 5, 2, '1010 Wilshire Blvd, CA, Los Angeles, USA', 'Los Angeles', '+1-310-555-5555', 4.0),
(4, 6, 2, '1212 Sunset Blvd, CA, Los Angeles, USA', 'Los Angeles', '+1-310-555-5556', 4.5),
(4, 3, 3, '1414 Melrose Ave, CA, Los Angeles, USA', 'Los Angeles', '+1-310-555-5557', 4.0),
(4, 4, 3, '1616 La Brea Ave, CA, Los Angeles, USA', 'Los Angeles', '+1-310-555-5558', 4.5),
(4, 5, 4, '1818 Santa Monica Blvd, CA, Los Angeles, USA', 'Los Angeles', '+1-310-555-5559', 5.0),
(5, 6, 0, '100 Main St, NY, New York, USA', 'New York', '+1-212-555-5552', 2.5),
(5, 3, 1, '200 Broadway, NY, New York, USA', 'New York', '+1-212-555-5553', 3.0),
(5, 4, 1, '300 5th Ave, NY, New York, USA', 'New York', '+1-212-555-5554', 3.5),
(5, 5, 2, '400 Park Ave, NY, New York, USA', 'New York', '+1-212-555-5555', 4.0),
(5, 6, 2, '500 Madison Ave, NY, New York, USA', 'New York', '+1-212-555-5556', 4.5),
(5, 3, 3, '600 Lexington Ave, NY, New York, USA', 'New York', '+1-212-555-5557', 4.0),
(5, 4, 3, '700 8th Ave, NY, New York, USA', 'New York', '+1-212-555-5558', 4.5),
(5, 5, 4, '800 7th Ave, NY, New York, USA', 'New York', '+1-212-555-5559', 5.0);

INSERT INTO Room(room_number, hotel_id, price_per_night, room_capacity, extension_capacity, tags, notes)
VALUES
(101, 1, 50, 1, 0, 'XX00000X', NULL),
(102, 1, 100, 2, 1, '00000XXX', NULL),
(103, 1, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 1, 200, 4, 0, 'XX00X00X', NULL),
(105, 1, 225, 5, 0, 'XXX0000X', NULL),
(101, 2, 50, 1, 0, 'XX00000X', NULL),
(102, 2, 100, 2, 1, '00000XXX', NULL),
(103, 2, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 2, 200, 4, 0, 'XX00X00X', NULL),
(105, 2, 225, 5, 0, 'XXX0000X', NULL),
(101, 3, 50, 1, 0, 'XX00000X', NULL),
(102, 3, 100, 2, 1, '00000XXX', NULL),
(103, 3, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 3, 200, 4, 0, 'XX00X00X', NULL),
(105, 3, 225, 5, 0, 'XXX0000X', NULL),
(101, 4, 50, 1, 0, 'XX00000X', NULL),
(102, 4, 100, 2, 1, '00000XXX', NULL),
(103, 4, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 4, 200, 4, 0, 'XX00X00X', NULL),
(105, 4, 225, 5, 0, 'XXX0000X', NULL),
(101, 5, 50, 1, 0, 'XX00000X', NULL),
(102, 5, 100, 2, 1, '00000XXX', NULL),
(103, 5, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 5, 200, 4, 0, 'XX00X00X', NULL),
(105, 5, 225, 5, 0, 'XXX0000X', NULL),
(101, 6, 50, 1, 0, 'XX00000X', NULL),
(102, 6, 100, 2, 1, '00000XXX', NULL),
(103, 6, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 6, 200, 4, 0, 'XX00X00X', NULL),
(105, 6, 225, 5, 0, 'XXX0000X', NULL),
(101, 7, 50, 1, 0, 'XX00000X', NULL),
(102, 7, 100, 2, 1, '00000XXX', NULL),
(103, 7, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 7, 200, 4, 0, 'XX00X00X', NULL),
(105, 7, 225, 5, 0, 'XXX0000X', NULL),
(101, 8, 50, 1, 0, 'XX00000X', NULL),
(102, 8, 100, 2, 1, '00000XXX', NULL),
(103, 8, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 8, 200, 4, 0, 'XX00X00X', NULL),
(105, 8, 225, 5, 0, 'XXX0000X', NULL),
(101, 9, 50, 1, 0, 'XX00000X', NULL),
(102, 9, 100, 2, 1, '00000XXX', NULL),
(103, 9, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 9, 200, 4, 0, 'XX00X00X', NULL),
(105, 9, 225, 5, 0, 'XXX0000X', NULL),
(101, 10, 50, 1, 0, 'XX00000X', NULL),
(102, 10, 100, 2, 1, '00000XXX', NULL),
(103, 10, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 10, 200, 4, 0, 'XX00X00X', NULL),
(105, 10, 225, 5, 0, 'XXX0000X', NULL),
(101, 11, 50, 1, 0, 'XX00000X', NULL),
(102, 11, 100, 2, 1, '00000XXX', NULL),
(103, 11, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 11, 200, 4, 0, 'XX00X00X', NULL),
(105, 11, 225, 5, 0, 'XXX0000X', NULL),
(101, 12, 50, 1, 0, 'XX00000X', NULL),
(102, 12, 100, 2, 1, '00000XXX', NULL),
(103, 12, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 12, 200, 4, 0, 'XX00X00X', NULL),
(105, 12, 225, 5, 0, 'XXX0000X', NULL),
(101, 13, 50, 1, 0, 'XX00000X', NULL),
(102, 13, 100, 2, 1, '00000XXX', NULL),
(103, 13, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 13, 200, 4, 0, 'XX00X00X', NULL),
(105, 13, 225, 5, 0, 'XXX0000X', NULL),
(101, 14, 50, 1, 0, 'XX00000X', NULL),
(102, 14, 100, 2, 1, '00000XXX', NULL),
(103, 14, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 14, 200, 4, 0, 'XX00X00X', NULL),
(105, 14, 225, 5, 0, 'XXX0000X', NULL),
(101, 15, 50, 1, 0, 'XX00000X', NULL),
(102, 15, 100, 2, 1, '00000XXX', NULL),
(103, 15, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 15, 200, 4, 0, 'XX00X00X', NULL),
(105, 15, 225, 5, 0, 'XXX0000X', NULL),
(101, 16, 50, 1, 0, 'XX00000X', NULL),
(102, 16, 100, 2, 1, '00000XXX', NULL),
(103, 16, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 16, 200, 4, 0, 'XX00X00X', NULL),
(105, 16, 225, 5, 0, 'XXX0000X', NULL),
(101, 17, 50, 1, 0, 'XX00000X', NULL),
(102, 17, 100, 2, 1, '00000XXX', NULL),
(103, 17, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 17, 200, 4, 0, 'XX00X00X', NULL),
(105, 17, 225, 5, 0, 'XXX0000X', NULL),
(101, 18, 50, 1, 0, 'XX00000X', NULL),
(102, 18, 100, 2, 1, '00000XXX', NULL),
(103, 18, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 18, 200, 4, 0, 'XX00X00X', NULL),
(105, 18, 225, 5, 0, 'XXX0000X', NULL),
(101, 19, 50, 1, 0, 'XX00000X', NULL),
(102, 19, 100, 2, 1, '00000XXX', NULL),
(103, 19, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 19, 200, 4, 0, 'XX00X00X', NULL),
(105, 19, 225, 5, 0, 'XXX0000X', NULL),
(101, 20, 50, 1, 0, 'XX00000X', NULL),
(102, 20, 100, 2, 1, '00000XXX', NULL),
(103, 20, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 20, 200, 4, 0, 'XX00X00X', NULL),
(105, 20, 225, 5, 0, 'XXX0000X', NULL),
(101, 21, 50, 1, 0, 'XX00000X', NULL),
(102, 21, 100, 2, 1, '00000XXX', NULL),
(103, 21, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 21, 200, 4, 0, 'XX00X00X', NULL),
(105, 21, 225, 5, 0, 'XXX0000X', NULL),
(101, 22, 50, 1, 0, 'XX00000X', NULL),
(102, 22, 100, 2, 1, '00000XXX', NULL),
(103, 22, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 22, 200, 4, 0, 'XX00X00X', NULL),
(105, 22, 225, 5, 0, 'XXX0000X', NULL),
(101, 23, 50, 1, 0, 'XX00000X', NULL),
(102, 23, 100, 2, 1, '00000XXX', NULL),
(103, 23, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 23, 200, 4, 0, 'XX00X00X', NULL),
(105, 23, 225, 5, 0, 'XXX0000X', NULL),
(101, 24, 50, 1, 0, 'XX00000X', NULL),
(102, 24, 100, 2, 1, '00000XXX', NULL),
(103, 24, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 24, 200, 4, 0, 'XX00X00X', NULL),
(105, 24, 225, 5, 0, 'XXX0000X', NULL),
(101, 25, 50, 1, 0, 'XX00000X', NULL),
(102, 25, 100, 2, 1, '00000XXX', NULL),
(103, 25, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 25, 200, 4, 0, 'XX00X00X', NULL),
(105, 25, 225, 5, 0, 'XXX0000X', NULL),
(101, 26, 50, 1, 0, 'XX00000X', NULL),
(102, 26, 100, 2, 1, '00000XXX', NULL),
(103, 26, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 26, 200, 4, 0, 'XX00X00X', NULL),
(105, 26, 225, 5, 0, 'XXX0000X', NULL),
(101, 27, 50, 1, 0, 'XX00000X', NULL),
(102, 27, 100, 2, 1, '00000XXX', NULL),
(103, 27, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 27, 200, 4, 0, 'XX00X00X', NULL),
(105, 27, 225, 5, 0, 'XXX0000X', NULL),
(101, 28, 50, 1, 0, 'XX00000X', NULL),
(102, 28, 100, 2, 1, '00000XXX', NULL),
(103, 28, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 28, 200, 4, 0, 'XX00X00X', NULL),
(105, 28, 225, 5, 0, 'XXX0000X', NULL),
(101, 29, 50, 1, 0, 'XX00000X', NULL),
(102, 29, 100, 2, 1, '00000XXX', NULL),
(103, 29, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 29, 200, 4, 0, 'XX00X00X', NULL),
(105, 29, 225, 5, 0, 'XXX0000X', NULL),
(101, 30, 50, 1, 0, 'XX00000X', NULL),
(102, 30, 100, 2, 1, '00000XXX', NULL),
(103, 30, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 30, 200, 4, 0, 'XX00X00X', NULL),
(105, 30, 225, 5, 0, 'XXX0000X', NULL),
(101, 31, 50, 1, 0, 'XX00000X', NULL),
(102, 31, 100, 2, 1, '00000XXX', NULL),
(103, 31, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 31, 200, 4, 0, 'XX00X00X', NULL),
(105, 31, 225, 5, 0, 'XXX0000X', NULL),
(101, 32, 50, 1, 0, 'XX00000X', NULL),
(102, 32, 100, 2, 1, '00000XXX', NULL),
(103, 32, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 32, 200, 4, 0, 'XX00X00X', NULL),
(105, 32, 225, 5, 0, 'XXX0000X', NULL),
(101, 33, 50, 1, 0, 'XX00000X', NULL),
(102, 33, 100, 2, 1, '00000XXX', NULL),
(103, 33, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 33, 200, 4, 0, 'XX00X00X', NULL),
(105, 33, 225, 5, 0, 'XXX0000X', NULL),
(101, 34, 50, 1, 0, 'XX00000X', NULL),
(102, 34, 100, 2, 1, '00000XXX', NULL),
(103, 34, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 34, 200, 4, 0, 'XX00X00X', NULL),
(105, 34, 225, 5, 0, 'XXX0000X', NULL),
(101, 35, 50, 1, 0, 'XX00000X', NULL),
(102, 35, 100, 2, 1, '00000XXX', NULL),
(103, 35, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 35, 200, 4, 0, 'XX00X00X', NULL),
(105, 35, 225, 5, 0, 'XXX0000X', NULL),
(101, 36, 50, 1, 0, 'XX00000X', NULL),
(102, 36, 100, 2, 1, '00000XXX', NULL),
(103, 36, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 36, 200, 4, 0, 'XX00X00X', NULL),
(105, 36, 225, 5, 0, 'XXX0000X', NULL),
(101, 37, 50, 1, 0, 'XX00000X', NULL),
(102, 37, 100, 2, 1, '00000XXX', NULL),
(103, 37, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 37, 200, 4, 0, 'XX00X00X', NULL),
(105, 37, 225, 5, 0, 'XXX0000X', NULL),
(101, 38, 50, 1, 0, 'XX00000X', NULL),
(102, 38, 100, 2, 1, '00000XXX', NULL),
(103, 38, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 38, 200, 4, 0, 'XX00X00X', NULL),
(105, 38, 225, 5, 0, 'XXX0000X', NULL),
(101, 39, 50, 1, 0, 'XX00000X', NULL),
(102, 39, 100, 2, 1, '00000XXX', NULL),
(103, 39, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 39, 200, 4, 0, 'XX00X00X', NULL),
(105, 39, 225, 5, 0, 'XXX0000X', NULL),
(101, 40, 50, 1, 0, 'XX00000X', NULL),
(102, 40, 100, 2, 1, '00000XXX', NULL),
(103, 40, 150, 3, 0, 'XX0XX00X', 'This room has a private pool.'),
(104, 40, 200, 4, 0, 'XX00X00X', NULL),
(105, 40, 225, 5, 0, 'XXX0000X', NULL);

--DELETE FROM HotelChain WHERE chain_name = 'Confort Inn';
--DELETE FROM Room;
--SELECT * FROM RoomArchive;
--SELECT * FROM Account;
