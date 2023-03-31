package Databases;

import Entities.BookingDisplay;
import Utilities.AccessResult;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BookingAccessor extends DatabaseAccessor{
    public BookingAccessor(Database database) {
        super(database);
    }

    // EXAMPLES OF QUERIES:
    // -> Get a list of booking with the room data for a given person
    // SELECT * FROM BOOKING NATURAL JOIN ROOM WHERE person_id = 0;

    public ArrayList<BookingDisplay> getBookingOfPerson (int personId) {
        AccessResult result = tryReturnStatement((conn) -> {
            ArrayList<BookingDisplay> bookings = new ArrayList<>();

            PreparedStatement statement = conn.prepareStatement(
                "SELECT booking_id, chain_name, address, start_date, end_date, room_capacity FROM Booking NATURAL JOIN Room NATURAL JOIN Hotel NATURAL JOIN HotelChain WHERE person_id = ?;");
            statement.setInt(1, personId);
            ResultSet results = statement.executeQuery();

            while(results.next()) {
                BookingDisplay display = new BookingDisplay();
                display.ReadFromResultSet(results, 1, false);
                bookings.add(display);
            }
            return new AccessResult(true, bookings);
        });
        return (ArrayList<BookingDisplay>) result.getResult();
    }

    public void deleteBookingIfBelongsToPersonId (int personId, int bookingId) {
        tryRunStatement((conn) -> {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM Booking WHERE person_id = ? AND booking_id = ?;");
            statement.setInt(1, personId);
            statement.setInt(2, bookingId);
            return statement.executeQuery().next();
        });
    }
}
