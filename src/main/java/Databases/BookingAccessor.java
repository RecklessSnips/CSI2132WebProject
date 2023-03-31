package Databases;

import Entities.*;
import Utilities.AccessResult;

import java.awt.print.Book;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Date;

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
                "SELECT chain_name, address, start_date, end_date, room_capacity FROM Booking NATURAL JOIN Room NATURAL JOIN Hotel NATURAL JOIN HotelChain WHERE person_id = ?;");
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


    public int createNewBooking(Room room, Person person, Date start, Date end) {
        AccessResult result = tryReturnStatement((conn) -> {

            Booking booking = new Booking(room.getRoomId(), person.getPersonId(), start, end);

            int bookingid = booking.WriteFromStatement(conn);

            return new AccessResult(true, bookingid);
        });

        if(result.didSucceed()) {
            return (int)result.getResult();
        } else {
            return 0;
        }
    }
}
