package Entities;

import Utilities.Address;
import Utilities.ISQLReadable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Hotel implements ISQLReadable {
    private int hotelId;
    private int chainId;
    private int managerId;
    private Address address;
    private String phone;
    private double rating;
    private int roomCount;
    private HotelType category;

    public int getHotelId() { return hotelId;}
    public int getChainId() { return chainId; }
    public int getManagerId() { return managerId; }
    public Address getAddress() { return address; }
    public String getPhone() { return phone; }
    public double getRating() { return rating; }
    public int getRoomCount() { return roomCount; }
    public HotelType getCategory() { return category; }

    public Hotel () {}

    @Override
    public void ReadFromResultSet(ResultSet resultSet) throws SQLException {
        hotelId = resultSet.getInt(1);
        chainId = resultSet.getInt(2);
        managerId = resultSet.getInt(3);
        address = Address.parseSQLAddress(resultSet.getString(4));
        phone = resultSet.getString(5);
        rating = resultSet.getDouble(6);
        roomCount = resultSet.getInt(7);
        category = HotelType.getEnum(resultSet.getInt(8));
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hotel_id=" + hotelId +
                ", chain_id=" + chainId +
                ", manager_id=" + managerId +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", rating='" + rating + '\'' +
                ", room_count=" + roomCount +
                '}';
    }
}
