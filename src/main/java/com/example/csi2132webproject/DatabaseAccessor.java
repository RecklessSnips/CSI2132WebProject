package com.example.csi2132webproject;
import java.sql.*;

public class DatabaseAccessor {

    private Connection connection = null;

    public boolean connect (String dbname, String dbport, String user, String password) {
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:"+dbport+"/"+dbname,user,password);
            return connection != null;
        }
        catch (Exception e) {
            System.out.println("Failed to connect to db: " + e.toString());
            return false;
        }
    }

    public boolean disconnect () {
        if(connection==null) return false;

        try{
            connection.close();
            return true;
        } catch (Exception e) {
            System.out.println("Failed to close connection to db: " + e.toString());
            return false;
        }
    }

    public boolean printHotelChains () {
        return tryRunStatement(() -> {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select chain_name from HotelChain");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("chain_name"));
            }

            return true;
        });
    }

    private boolean tryRunStatement (AccessFunction function) {
        if(connection==null) return false;
        try {
            return function.tryRun();
        } catch (Exception e) {
            System.out.println("Failed to run SQL statement: " + e.toString());
            return false;
        }
    }
}

interface AccessFunction {
    boolean tryRun() throws SQLException;
}
