package Entities;

public class Room {

    private int room_id;
    private int room_number;
    private int hotel_id;
    private double price_per_night;
    private int room_capacity;
    private int extension_capacity;
    private int tags;
    private String notes;

    private Hotel hotel;

    public Room(int room_id, int room_number, Hotel hotel, double price_per_night, int room_capacity, int extension_capacity, int tags, String notes) {
        this.room_id = room_id;
        this.room_number = room_number;
        this.hotel_id = hotel.getHotel_id();
        this.price_per_night = price_per_night;
        this.room_capacity = room_capacity;
        this.extension_capacity = extension_capacity;
        this.tags = tags;
        this.notes = notes;
        this.hotel = hotel;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getRoom_number() {
        return room_number;
    }

    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public double getPrice_per_night() {
        return price_per_night;
    }

    public void setPrice_per_night(double price_per_night) {
        this.price_per_night = price_per_night;
    }

    public int getRoom_capacity() {
        return room_capacity;
    }

    public void setRoom_capacity(int room_capacity) {
        this.room_capacity = room_capacity;
    }

    public int getExtension_capacity() {
        return extension_capacity;
    }

    public void setExtension_capacity(int extension_capacity) {
        this.extension_capacity = extension_capacity;
    }

    public int getTags() {
        return tags;
    }

    public void setTags(int tags) {
        this.tags = tags;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getHotelId() {
        return hotel.getHotel_id();
    }
}
