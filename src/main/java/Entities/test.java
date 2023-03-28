package Entities;

import java.sql.*;

public class test{
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String JD_BC = "com.mysql.cj.jdbc.Driver";
        /* 1. Use the forName to register the Driver class
        into the memory
        * */
        Class.forName(JD_BC);

        // 2. Create connection:
        // * use the driver manager to help use the Driver class
        String database = "jdbc:mysql://localhost:3306/2132Project";
        String user = "root";
        String password = "19960214At";
        Connection connection = DriverManager.getConnection(database, user, password);

        // 3. Execute query
        Statement statement = connection.createStatement();
        String select = "select * from account";
        String insert = "insert into account values('Dooku', 45)";
//        statement.executeUpdate(insert);
        /*
            A result set is everything in a table, by using the ResultSet
        class, we encapsulate everything record from the table into the object

            The executeQuery function execute the query which we write
        */
        ResultSet resultSet = statement.executeQuery(select);
        // get columns in table
        int columns = resultSet.getMetaData().getColumnCount();
        while (resultSet.next()){
            String name = resultSet.getString("name");
            String age = resultSet.getString("password");
            System.out.printf("%s %s\n", name, age);
        }
        resultSet.close();
        statement.close();
        connection.close();
    }
}
