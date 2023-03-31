package Entities;

import Utilities.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Person implements ISQLReadable, ISQLWritable, ISQLUpdatable, ISQLDeletable {
    private int personId;
    private String firstName;
    private String lastName;
    private Address address;

    public void setPersonId(int personId) { this.personId = personId; }
    public int getPersonId() { return personId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Address getAddress() { return address; }
    public void setFirstName (String firstName) {this.firstName = firstName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public void setAddress(Address address) {this.address = address;}

    
    public Person () {}
    public Person (String firstName, String lastName, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public final String insertQuery =
            "INSERT INTO Person(first_name, last_name, address)VALUES (?,?,?);";
    public final String updateQuery =
            "UPDATE Person SET first_name=?, last_name=?, address=? WHERE personID = ?;";
    public final String deleteQuery =
            "DELETE FROM Person WHERE personID = ?;";

    @Override
    public void ReadFromResultSet(ResultSet resultSet, int startColumn, boolean excludeId) throws SQLException {
        if(!excludeId) {
            startColumn--;
            personId = resultSet.getInt(1 + startColumn);
        } else {
            startColumn-=2;
        }
        firstName = resultSet.getString(2 + startColumn);
        lastName = resultSet.getString(3 + startColumn);
        address = Address.parseSQLAddress(resultSet.getString(4 + startColumn));
    }

    @Override
    public int WriteFromStatement(Connection conn) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1, firstName);
        statement.setString(2, lastName);
        statement.setString(3, address.toString());
        statement.executeQuery();
        ResultSet rs = statement.getGeneratedKeys();
        personId = rs.getInt(1);
        return personId;
    }

    @Override
    public void UpdateFromStatement(Connection conn) throws SQLException {
        if(personId == 0) throw new SQLException("Trying to update Person with uninitialized ID");

        PreparedStatement statement = conn.prepareStatement(updateQuery);
        statement.setString(1, firstName);
        statement.setString(2, lastName);
        statement.setString(3, address.toString());
        statement.setInt(4, personId);
        statement.executeQuery();
    }

    @Override
    public void DeleteFromStatement(Connection conn) throws SQLException {
        if(personId == 0) throw new SQLException("Trying to delete Person with uninitialized ID");

        PreparedStatement statement = conn.prepareStatement(deleteQuery);
        statement.setInt(1, personId);
        statement.executeQuery();
    }

    @Override
    public String toString() {
        return "Person{" +
                "personID=" + personId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                ", insertQuery='" + insertQuery + '\'' +
                '}';
    }
}
