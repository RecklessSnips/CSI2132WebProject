package Entities;

import Utilities.ISQLReadable;
import Utilities.ISQLUpdatable;
import Utilities.PasswordHasher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountCredential implements ISQLReadable, ISQLUpdatable {
    private int accountId;
    private String salt;
    private String hashedSaltPassword;

    public void setAccountId (int accountId){ this.accountId = accountId;}
    public String getSalt() {return salt;}
    public String getHash() {return hashedSaltPassword;}

    public AccountCredential() {}
    public AccountCredential(String username, String password) {
        salt = PasswordHasher.generateSalt();
        hashedSaltPassword = PasswordHasher.hash(username, password, salt);
    }

    public final String updateQuery =
            "UPDATE Account SET salt=?, hashed_salted_password=?, WHERE account_id = ?;";

    @Override
    public void ReadFromResultSet(ResultSet resultSet, int startColumn, boolean excludeId) throws SQLException {
        if(!excludeId) {
            startColumn--;
            accountId = resultSet.getInt(1 + startColumn);
        } else {
            startColumn-=2;
        }
        salt = resultSet.getString(7 + startColumn);
        hashedSaltPassword = resultSet.getString(8 + startColumn);
    }

    @Override
    public void UpdateFromStatement(Connection conn) throws SQLException {
        if (accountId == 0) throw new SQLException("Invalid account ID");

        PreparedStatement statement = conn.prepareStatement(updateQuery);
        statement.setString(1, salt);
        statement.setString(2, hashedSaltPassword);
    }
}
