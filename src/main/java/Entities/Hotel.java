package Entities;

import Utilities.Address;

public class Hotel {

    private int hotelId;
    private int chainId;
    private int managerId;
    private Address address;
    private String phone;
    private float rating;
    private int roomCount;
    private HotelCategory category;
    private HotelChain hotelChain; // TODO: Figure out how to load


    public int getHotelId() {
        return hotelId;
    }
    public int getChainId() {
        return chainId;
    }
    public int getManager_id() {
        return managerId;
    }
    public Address getAddress() {
        return address;
    }
    public String getPhone() {
        return phone;
    }
    public float getRating() {
        return rating;
    }
    public int getRoomCount() {
        return roomCount;
    }
    public HotelCategory getCategory() {return category;}
    public String getChainName() {
        return "This hotel belongs to: " + hotelChain.getChain_name();
    }

    public Hotel(int hotelId, HotelChain hotelChain, int managerId, Address address, String phone, float rating, int roomCount, HotelCategory category) {
        this.hotelId = hotelId;
        this.chainId = hotelChain.getChain_id();
        this.managerId = managerId;
        this.address = address;
        this.phone = phone;
        this.rating = rating;
        this.roomCount = roomCount;
        this.category = category;
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
