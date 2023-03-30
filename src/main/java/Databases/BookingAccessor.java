package Databases;

public class BookingAccessor extends DatabaseAccessor{
    public BookingAccessor(Database database) {
        super(database);
    }

    // EXAMPLES OF QUERIES:
    // -> Get a list of booking with the room data for a given person
    // SELECT * FROM BOOKING NATURAL JOIN ROOM WHERE person_id = 0;
}
