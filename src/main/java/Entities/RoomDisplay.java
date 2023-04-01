package Entities;

import Utilities.Address;
import Utilities.ISQLReadable;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomDisplay implements ISQLReadable {
    public int roomId;
    public int roomNumber;
    public double roomPrice;
    public String chainName;
    public Address address;
    public int roomTags;
    public int roomCapacity;
    public int extensionCapacity;

    public RoomDisplay() {}

    @Override
    public void ReadFromResultSet(ResultSet resultSet, int startColumn, boolean excludeId) throws SQLException {
        roomId = resultSet.getInt(1);
        roomNumber = resultSet.getInt(2);
        chainName = resultSet.getString(3);
        address = Address.parseSQLAddress(resultSet.getString(4));
        roomTags = Integer.parseInt(resultSet.getString(5).replace('X','1').trim(), 2);
        roomCapacity = resultSet.getInt(6);
        extensionCapacity = resultSet.getInt(7);
        roomPrice = resultSet.getInt(8);
    }
}