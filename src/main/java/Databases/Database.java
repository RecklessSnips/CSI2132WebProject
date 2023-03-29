package Databases;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private final String JDBC = "org.postgresql.Driver";
    private final String URL = "jdbc:postgresql://localhost:";
    private final String USER = "postgres";
    private final String DBPORT = "5433";
    private final String DBNAME = "HotelManager";
    private final String PASSWORD = "admin";
    private Connection connection = null;

    public boolean connect () {
        try{
            Class.forName(JDBC);
            connection = DriverManager.getConnection(URL+DBPORT+"/"+DBNAME,USER,PASSWORD);
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

    public Connection getConnection () {
        return connection;
    }
}
