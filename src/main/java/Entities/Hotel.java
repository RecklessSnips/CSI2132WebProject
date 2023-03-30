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
    private String area;
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
    public void ReadFromResultSet(ResultSet resultSet, int startColumn, boolean excludeId) throws SQLException {
        if(!excludeId) {
            startColumn--;
            hotelId = resultSet.getInt(1 + startColumn);
        } else {
            startColumn-=2;
        }
        chainId = resultSet.getInt(2 + startColumn);
        managerId = resultSet.getInt(3 + startColumn);
        address = Address.parseSQLAddress(resultSet.getString(4 + startColumn));
        area = resultSet.getString(5 + startColumn);
        phone = resultSet.getString(6 + startColumn);
        rating = resultSet.getDouble(7 + startColumn);
        roomCount = resultSet.getInt(8 + startColumn);
        category = HotelType.getEnum(resultSet.getInt(9 + startColumn));
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hotel_id=" + hotelId +
                ", chain_id=" + chainId +
                ", manager_id=" + managerId +
                ", address='" + address + '\'' +
                ", area='" + area + '\'' +
                ", phone='" + phone + '\'' +
                ", rating='" + rating + '\'' +
                ", room_count=" + roomCount +
                '}';
    }
}
