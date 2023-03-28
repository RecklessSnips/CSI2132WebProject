package Entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountCredidentials implements ISQLParsable {
    private int accountId;
    private int personId;
    private String username;
    private String salt;
    private String hashedSaltPassword;

    @Override
    public void ReadFromResultSet(ResultSet resultSet) throws SQLException {
        accountId = resultSet.getInt(0);
        personId = resultSet.getInt(1);
        username = resultSet.getString(2);
        salt = resultSet.getString(6);
        hashedSaltPassword = resultSet.getString(7);
    }

    public String getSalt() { return salt; }

    public String getHash() { return hashedSaltPassword; }
}
