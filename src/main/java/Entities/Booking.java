package Entities;

import Utilities.ISQLDeletable;
import Utilities.ISQLReadable;
import Utilities.ISQLUpdatable;
import Utilities.ISQLWritable;

import java.sql.*;

// Can be also used for "Renting"
public class Booking implements ISQLReadable, ISQLWritable, ISQLUpdatable, ISQLDeletable {
    private int bookingId;
    private int roomId;
    private int personId;
    private Date startDate;
    private Date endDate;

    public int getBookingId() { return bookingId; }
    public int getRoomId() { return roomId; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public void setStartDate (Date date) { this.startDate = date; }
    public void setEndDate (Date date) { this.endDate = date; }

    public Booking () {}
    public Booking (int roomId, int personId, Date startDate, Date endDate) {
        this.roomId = roomId;
        this.personId = personId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public final String insertQuery =
            "INSERT INTO Booking(room_id, person_id, start_date, start_end)VALUES (?,?,?,?);";
    public final String updateQuery =
            "UPDATE Booking SET start_date=?, end_date=? WHERE bookingId = ?;";
    public final String deleteQuery =
            "DELETE FROM Booking WHERE bookingId = ?;";

    @Override
    public void ReadFromResultSet(ResultSet resultSet, int startColumn, boolean excludeId) throws SQLException {
        if(!excludeId) {
            startColumn--;
            bookingId = resultSet.getInt(1 + startColumn);
        } else {
            startColumn-=2;
        }
        roomId = resultSet.getInt(2 + startColumn);
        personId = resultSet.getInt(3 + startColumn);
        startDate = resultSet.getDate(4 + startColumn);
        endDate = resultSet.getDate(5 + startColumn);
    }

    @Override
    public int WriteFromStatement(Connection conn) throws SQLException {
        if(roomId == 0) throw new SQLException("Invalid room ID");
        if(personId == 0) throw new SQLException("Invalid person ID");

        PreparedStatement statement = conn.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setInt(1, roomId);
        statement.setInt(2, personId);
        statement.setDate(3, startDate);
        statement.setDate(4, endDate);
        statement.executeQuery();

        ResultSet rs = statement.getGeneratedKeys();
        bookingId = rs.getInt(1);
        return bookingId;
    }

    @Override
    public void UpdateFromStatement(Connection conn) throws SQLException {
        if(bookingId == 0) throw new SQLException("Invalid booking ID");

        PreparedStatement statement = conn.prepareStatement(updateQuery);
        statement.setDate(1, startDate);
        statement.setDate(2, endDate);
        statement.setInt(3, bookingId);
        statement.executeQuery();
    }

    @Override
    public void DeleteFromStatement(Connection conn) throws SQLException {
        if(bookingId == 0) throw new SQLException("Invalid booking ID");

        PreparedStatement statement = conn.prepareStatement(deleteQuery);
        statement.setInt(1, bookingId);
        statement.executeQuery();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", roomId=" + roomId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", insertQuery='" + insertQuery + '\'' +
                ", updateQuery='" + updateQuery + '\'' +
                ", deleteQuery='" + deleteQuery + '\'' +
                '}';
    }
}
