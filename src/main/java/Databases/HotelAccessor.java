package Databases;

import Entities.Hotel;
import Entities.HotelChain;
import Entities.Room;
import Utilities.AccessResult;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HotelAccessor extends DatabaseAccessor {
    public HotelAccessor(Database database) {
        super(database);
    }

    public HotelChain getHotelChainFromId (int hotelChainId) {
        AccessResult acc = tryReturnStatement((conn) -> {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM HotelChain WHERE chain_id = ?");
            statement.setInt(1, hotelChainId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                HotelChain hotelChain = new HotelChain();
                hotelChain.ReadFromResultSet(resultSet, 1, false);
                return new AccessResult(true, hotelChain);
            }
            return AccessResult.failed();
        });
        return (HotelChain)acc.getResult();
    }

    public Hotel getHotelFromId (int hotelId) {
        AccessResult acc = tryReturnStatement((conn) -> {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Hotel WHERE hotel_id = ?");
            statement.setInt(1, hotelId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                Hotel hotel = new Hotel();
                hotel.ReadFromResultSet(resultSet, 1, false);
                return new AccessResult(true, hotel);
            }
            return AccessResult.failed();
        });
        return (Hotel)acc.getResult();
    }
}
