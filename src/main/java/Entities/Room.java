package Entities;

import Utilities.ISQLReadable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Room implements ISQLReadable {
    private int roomId;
    private int roomNumber;
    private int hotelId;
    private double pricePerNight;
    private int roomCapacity;
    private int extensionCapacity;
    private int tags;
    private String notes;

    public int getRoomId() { return roomId; }
    public int getRoomNumber() { return roomNumber; }
    public int getHotelId() { return hotelId; }
    public double getPricePerNight() { return pricePerNight; }
    public int getRoomCapacity() { return roomCapacity; }
    public int getExtensionCapacity() { return extensionCapacity; }
    public boolean checkIfRoomHasTag (RoomTags tag) { return tag.checkIfTagIsPresent(this.tags);}
    public String getNotes() { return notes; }

    public Room () {}

    @Override
    public void ReadFromResultSet(ResultSet resultSet, int startColumn, boolean excludeId) throws SQLException {
        if(!excludeId) {
            startColumn--;
            roomId = resultSet.getInt(1 + startColumn);
        } else {
            startColumn-=2;
        }
        roomNumber = resultSet.getInt(2 + startColumn + startColumn);
        hotelId = resultSet.getInt(3 + startColumn);
        pricePerNight = resultSet.getDouble(4 + startColumn);
        roomCapacity = resultSet.getInt(5 + startColumn);
        extensionCapacity = resultSet.getInt(6 + startColumn);
        tags = Integer.parseInt(resultSet.getString(7 + startColumn).replace('X', '1'),2);
        notes = resultSet.getString(8 + startColumn);
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", roomNumber=" + roomNumber +
                ", hotelId=" + hotelId +
                ", pricePerNight=" + pricePerNight +
                ", roomCapacity=" + roomCapacity +
                ", extensionCapacity=" + extensionCapacity +
                ", tags=" + tags +
                ", notes='" + notes + '\'' +
                '}';
    }
}
