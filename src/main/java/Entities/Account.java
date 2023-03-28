package Entities;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account implements ISQLParsable {
    private int accountId;
    private int personId;
    private String username;
    private String ssnSin;
    private Date creationDate;
    private AccountType type;

    public int getAccountId() { return accountId; }
    public int getPersonId() { return personId; }
    public String getUsername() { return username; }
    public String getSsnSin() { return ssnSin; }
    public Date getCreationDate() { return creationDate; }

    @Override
    public void ReadFromResultSet(ResultSet resultSet) throws SQLException {
        accountId = resultSet.getInt(0);
        personId = resultSet.getInt(1);
        username = resultSet.getString(2);
        ssnSin = resultSet.getString(3);
        creationDate = resultSet.getDate(4);
        type = AccountType.values()[resultSet.getInt(5)];
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
