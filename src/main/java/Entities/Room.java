package Entities;

public class Room {

    private int roomId;
    private int roomNumber;
    private int hotelId;
    private double pricePerNight;
    private int roomCapacity;
    private int extensionCapacity;
    private int tags;  // TODO: This will get changed later into something else, not sure what yet
    private String notes;
    private Hotel hotel; // TODO: Figure out how to load

    public int getRoomId() {
        return roomId;
    }
    public int getRoomNumber() {
        return roomNumber;
    }
    public int getHotelId() {
        return hotelId;
    }
    public double getPricePerNight() {
        return pricePerNight;
    }
    public int getRoomCapacity() {
        return roomCapacity;
    }
    public int getExtensionCapacity() {
        return extensionCapacity;
    }
    public int getTags() {
        return tags;
    }
    public String getNotes() {
        return notes;
    }

    public Room(int roomId, int room_number, Hotel hotel, double pricePerNight, int roomCapacity, int extensionCapacity, int tags, String notes) {
        this.roomId = roomId;
        this.roomNumber = room_number;
        this.hotelId = hotel.getHotelId();
        this.pricePerNight = pricePerNight;
        this.roomCapacity = roomCapacity;
        this.extensionCapacity = extensionCapacity;
        this.tags = tags;
        this.notes = notes;
        this.hotel = hotel;
    }


}
