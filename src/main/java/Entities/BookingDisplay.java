package Entities;

import Utilities.Address;
import Utilities.ISQLReadable;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingDisplay implements ISQLReadable {
    public String chainName;
    public Address address;
    public Date startDate;
    public Date endDate;
    public int roomCapacity;

    public BookingDisplay(){}

    @Override
    public void ReadFromResultSet(ResultSet resultSet, int startColumn, boolean excludeId) throws SQLException {
        chainName = resultSet.getString(1);
        address = Address.parseSQLAddress(resultSet.getString(2));
        startDate = resultSet.getDate(3);
        endDate = resultSet.getDate(4);
        roomCapacity = resultSet.getInt(5);
    }
}
