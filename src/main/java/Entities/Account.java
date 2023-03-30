package Entities;

import Utilities.ISQLDeletable;
import Utilities.ISQLReadable;
import Utilities.ISQLWritable;

import java.sql.*;

public class Account implements ISQLReadable, ISQLWritable, ISQLDeletable {
    private int accountId;
    private int personId;
    private String username;
    private String ssnSin;
    private Date creationDate;
    private AccountType type;

    public void setPersonId (int personId) { this.personId = personId;}
    public int getAccountId() { return accountId; }
    public int getPersonId() { return personId; }
    public String getUsername() { return username; }
    public String getSsnSin() { return ssnSin; }
    public Date getCreationDate() { return creationDate; }

    public Account () {}
    public Account (String username, String ssnSin, AccountType type) {
        this.username = username;
        this.ssnSin = ssnSin;
        this.type = type;
    }

    public final String insertQuery =
        "INSERT INTO Account(person_id, account_type, username, ssn_sin, creation_date)VALUES (?,?,?,?,NOW())";
    public final String deleteQuery =
        "DELETE FROM Account WHERE account_id = ?;";

    @Override
    public void ReadFromResultSet(ResultSet resultSet, int startColumn, boolean excludeId) throws SQLException {
        if(!excludeId) {
            startColumn--;
            accountId = resultSet.getInt(1 + startColumn);
        } else {
            startColumn-=2;
        }
        personId = resultSet.getInt(2 + startColumn);
        username = resultSet.getString(3 + startColumn);
        ssnSin = resultSet.getString(4 + startColumn);
        creationDate = resultSet.getDate(5 + startColumn);
        type = AccountType.getEnum(resultSet.getInt(6 + startColumn));
    }

    @Override
    public int WriteFromStatement(Connection conn) throws SQLException {
        if(personId == 0) throw new SQLException("Invalid person ID");

        PreparedStatement statement = conn.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setInt(1, personId);
        statement.setInt(2, type.getValue());

        ResultSet rs = statement.getGeneratedKeys();
        accountId = rs.getInt(1);
        return accountId;
    }

    @Override
    public void DeleteFromStatement(Connection conn) throws SQLException {
        if(accountId == 0) throw new SQLException("Invalid account ID");

        PreparedStatement statement = conn.prepareStatement(deleteQuery);
        statement.setInt(1, accountId);
    }

    @Override
    public String toString() {
        return "Account{" +
                "account_id=" + accountId +
                ", person_id=" + personId +
                ", username='" + username +
                "\', ssn_sin=" + ssnSin +
                ", creation_date='" + creationDate +
                "\', type =" + type +
                '}';
    }
}
