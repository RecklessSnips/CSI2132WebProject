package Databases;

import Entities.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PersonDB extends DatabaseAccessor{
    Person person;
    private Person getByName(String name){
        Statement statement = null;
        String selectQuery;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            selectQuery = "select * from Person where first_name ='" + name + "'";
            resultSet = statement.executeQuery(selectQuery);
            if (resultSet.next()){
//                person = new Person(resultSet.getString("first_name"),
//                        resultSet.getString("last_name"),
//                        resultSet.getString("address"));
                person.ReadFromResultSet(resultSet);
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return person;
    }
}
