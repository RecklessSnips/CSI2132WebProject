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
    private HotelType category;

    public int getHotelId() { return hotelId;}
    public int getChainId() { return chainId; }
    public int getManagerId() { return managerId; }
    public Address getAddress() { return address; }
    public String getPhone() { return phone; }
    public double getRating() { return rating; }
    public HotelType getCategory() { return category; }

    public String getArea() {
        return area;
    }

    public Hotel () {}
    public Hotel(int hotelId, int chainId, int managerId, Address address, String area, String phone, double rating, HotelType category) {
        this.hotelId = hotelId;
        this.chainId = chainId;
        this.managerId = managerId;
        this.address = address;
        this.area = area;
        this.phone = phone;
        this.rating = rating;
        this.category = category;
    }

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
        category = HotelType.getEnum(resultSet.getInt(8 + startColumn));
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
                '}';
    }
}
