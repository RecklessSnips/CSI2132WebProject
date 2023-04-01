package Databases;

import Entities.*;
import Utilities.AccessResult;
import Utilities.Address;
import Utilities.Pair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Date;

public class RoomAccessor extends DatabaseAccessor {
    public RoomAccessor(Database database) {
        super(database);
    }

    // EXAMPLES OF QUERIES:
    // -> Return the amount of available rooms from a certain hotel
    // SELECT COUNT(*) FROM ROOM WHERE hotel_id = 1
    // -> Return the available rooms per area
    // SELECT * FROM ROOM NATURAL JOIN HOTEL WHERE area = 'Montreal';
    // -> Get a list of areas
    // SELECT DISTINCT area FROM HOTEL;

    public ArrayList<RoomDisplay> getRoomsWithConditions(
            boolean categoryEnabled,
            boolean areaEnabled,
            boolean roomCapacityEnabled,
            boolean chainEnabled,
            boolean priceMinEnabled,
            boolean priceMaxEnabled,
            boolean timeEnabled,
            HotelType hotelType,
            String area,
            int roomCapacity,
            int chainId,
            double priceMinPoint,
            double priceMaxPoint,
            Date startDate,
            Date endDate) {

        int conditionCount =
            (categoryEnabled ? 1 : 0) +
            (areaEnabled ? 1 : 0) +
            (roomCapacityEnabled ? 1 : 0) +
            (chainEnabled ? 1 : 0) +
            (priceMinEnabled ? 1 : 0) +
            (priceMaxEnabled ? 1 : 0)+
            (timeEnabled ? 1 : 0);

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT room_id, room_number, chain_name, address, tags, room_capacity, extension_capacity, price_per_night, rating FROM Room NATURAL JOIN Hotel NATURAL JOIN HotelChain ");
        if(conditionCount > 0) {
            queryBuilder.append(" WHERE ");
            if(categoryEnabled) {
                queryBuilder.append("category = ?");
                conditionCount--;
                if(conditionCount > 0) queryBuilder.append(" AND ");
            }

            if(areaEnabled) {
                queryBuilder.append("area = ?");
                conditionCount--;
                if(conditionCount > 0) queryBuilder.append(" AND ");
            }

            if(roomCapacityEnabled) {
                queryBuilder.append("room_capacity = ?");
                conditionCount--;
                if(conditionCount > 0) queryBuilder.append(" AND ");
            }

            if(chainEnabled) {
                queryBuilder.append("chain_id = ?");
                conditionCount--;
                if(conditionCount > 0) queryBuilder.append(" AND ");
            }

            if(priceMinEnabled) {
                queryBuilder.append("price_per_night >= ?");
                conditionCount--;
                if(conditionCount > 0) queryBuilder.append(" AND ");
            }

            if(priceMaxEnabled) {
                queryBuilder.append("price_per_night <= ?");
                conditionCount--;
                if(conditionCount > 0) queryBuilder.append(" AND ");
            }

            if(timeEnabled) {
                queryBuilder.append("room_id NOT IN (SELECT room_id FROM Booking WHERE start_date < ? AND end_date > ?) ");

                conditionCount--;
                if(conditionCount > 0) queryBuilder.append(" AND ");
            }
        }
        queryBuilder.append(" LIMIT 50;");
        System.out.println(queryBuilder.toString());

        AccessResult acc = tryReturnStatement((conn) -> {
            PreparedStatement statement = conn.prepareStatement(queryBuilder.toString());

            int index = 1;
            if(categoryEnabled) {
                statement.setInt(index, hotelType.getValue());
                index++;
            }
            if(areaEnabled) {
                statement.setString(index, area);
                index++;
            }
            if(roomCapacityEnabled) {
                statement.setInt(index, roomCapacity);
                index++;
            }
            if(chainEnabled) {
                statement.setInt(index, chainId);
                index++;
            }
            if(priceMinEnabled) {
                statement.setDouble(index, priceMinPoint);
                index++;
            }
            if(priceMaxEnabled) {
                statement.setDouble(index, priceMaxPoint);
                index++;
            }
            if(timeEnabled) {
                statement.setDate(index, startDate);
                index++;
                statement.setDate(index, endDate);
                index++;
            }

            ArrayList<RoomDisplay> rooms = new ArrayList<>();
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                RoomDisplay roomDisplay = new RoomDisplay();
                roomDisplay.ReadFromResultSet(results, 1, false);
                rooms.add(roomDisplay);
            }

            return new AccessResult(true, rooms);
        });

        return (ArrayList<RoomDisplay>) acc.getResult();
    }

    public Room getRoomFromId (int roomId) {
        AccessResult acc = tryReturnStatement((conn) -> {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Room WHERE room_id = ?");
            statement.setInt(1, roomId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                Room room = new Room();
                room.ReadFromResultSet(resultSet, 1, false);
                return new AccessResult(true, room);
            }
            return AccessResult.failed();
        });
        return (Room)acc.getResult();
    }

    public Room getRoomFromHotelAndNumber (int roomNumber, int hotelId) {
        AccessResult acc = tryReturnStatement((conn) -> {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Room WHERE room_number = ? AND hotel_id = ?;");
            statement.setInt(1, roomNumber);
            statement.setInt(2, hotelId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                Room room = new Room();
                room.ReadFromResultSet(resultSet, 1, false);
                return new AccessResult(true, room);
            }
            return AccessResult.failed();
        });
        return (Room)acc.getResult();
    }
}
