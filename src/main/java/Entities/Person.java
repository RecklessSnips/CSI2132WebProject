package Entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Person implements ISQLParsable {
    public int personID;
    public String firstName;
    public String lastName;
    public Address address;

    @Override
    public void ReadFromResultSet(ResultSet resultSet) throws SQLException {
        personID = resultSet.getInt(0);
        firstName = resultSet.getString(1);
        lastName = resultSet.getString(2);
        address = Address.parseSQLAddress(resultSet.getString(3));
    }

    public int getPersonID() {return personID;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public Address getAddress() {return address;}
}
