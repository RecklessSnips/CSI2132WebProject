package Entities;

import Utilities.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Person implements ISQLReadable, ISQLWritable, ISQLUpdatable, ISQLDeletable {
    private int personID;
    private String firstName;
    private String lastName;
    private Address address;

    public int getPersonID() { return personID; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Address getAddress() { return address; }

    public final String insertQuery =
            "INSERT INTO Person(first_name, last_name, address)VALUES (?,?,?);";
    public final String updateQuery =
            "UPDATE Person SET first_name=?, last_name=?, address=? WHERE personID = ?;";
    public final String deleteQuery =
            "DELETE FROM Person WHERE personID = ?;";

    @Override
    public void ReadFromResultSet(ResultSet resultSet) throws SQLException {
        personID = resultSet.getInt(1);
        firstName = resultSet.getString(2);
        lastName = resultSet.getString(3);
        address = Address.parseSQLAddress(resultSet.getString(4));
    }

    @Override
    public int WriteFromStatement(Connection conn) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1, firstName);
        statement.setString(2, lastName);
        statement.setString(3, address.toString());
        statement.executeQuery();
        ResultSet rs = statement.getGeneratedKeys();
        personID = rs.getInt(1);
        return personID;
    }

    @Override
    public void UpdateFromStatement(Connection conn) throws SQLException {
        if(personID == 0) throw new SQLException("Trying to update Person with uninitialized ID");

        PreparedStatement statement = conn.prepareStatement(updateQuery);
        statement.setString(1, firstName);
        statement.setString(2, lastName);
        statement.setString(3, address.toString());
        statement.setInt(4, personID);
        statement.executeQuery();
    }

    @Override
    public void DeleteFromStatement(Connection conn) throws SQLException {
        if(personID == 0) throw new SQLException("Trying to delete Person with uninitialized ID");

        PreparedStatement statement = conn.prepareStatement(deleteQuery);
        statement.setInt(1, personID);
        statement.executeQuery();
    }

    @Override
    public String toString() {
        return "Person{" +
                "personID=" + personID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                ", insertQuery='" + insertQuery + '\'' +
                '}';
    }
}
