package Entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Person implements ISQLParsable {
    public int personID;
    public String firstName;
    public String lastName;
    public String address;


    @Override
    public void ReadFromResultSet(ResultSet resultSet) throws SQLException {
        personID = resultSet.getInt(0);
        firstName = resultSet.getString(1);
        lastName = resultSet.getString(2);
        address = resultSet.getString(3);
    }
}
