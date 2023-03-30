package Databases;

import Utilities.AccessResult;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RoomAccessor extends DatabaseAccessor {
    public RoomAccessor(Database database) {
        super(database);
    }

    // EXAMPLES OF QUERIES:
    // -> Return the amount of available rooms from a certain hotel
    // SELECT COUNT(*) FROM ROOM WHERE hotel_id = 1
    // -> Return the available rooms per area
    // SELECT * FROM ROOM NATURAL JOIN HOTEL WHERE area = 'Montreal';
    // -> Get a list of areas
    // SELECT DISTINCT area FROM HOTEL;
}
