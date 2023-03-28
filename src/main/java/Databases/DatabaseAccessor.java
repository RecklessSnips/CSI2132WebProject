package Databases;
import java.sql.*;

public abstract class DatabaseAccessor {
    private final String JDBC = "org.postgresql.Driver";
    private final String URL = "jdbc:postgresql://localhost:";
    private final String USER = "postgres";
    private final String DBPORT = "5433";
    private final String DBNAME = "HotelManager";
    private final String PASSWORD = "admin";
    protected Connection connection = null;

//    public boolean connect (String dbname, String dbport, String user, String password)
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
