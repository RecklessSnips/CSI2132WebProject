package Entities;

import Utilities.Address;
import Utilities.ISQLReadable;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingDisplay implements ISQLReadable {
    public int bookingId;
    public String chainName;
    public Address address;
    public Date startDate;
    public Date endDate;
    public int roomCapacity;

    public BookingDisplay(){}

    @Override
    public void ReadFromResultSet(ResultSet resultSet, int startColumn, boolean excludeId) throws SQLException {
        bookingId = resultSet.getInt(1);
        chainName = resultSet.getString(2);
        address = Address.parseSQLAddress(resultSet.getString(3));
        startDate = resultSet.getDate(4);
        endDate = resultSet.getDate(5);
        roomCapacity = resultSet.getInt(6);
    }
}
