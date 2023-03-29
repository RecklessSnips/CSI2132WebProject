package Databases;

import Entities.AccountCredential;
import Utilities.AccessResult;
import Utilities.PasswordHasher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountAccessor extends DatabaseAccessor{

    public AccountAccessor(Database database) {
        super(database);
    }

    private AccountCredential checkForAccountWithUsername (String username) {
        AccessResult result = tryReturnStatement((conn) -> {

            // Creating a SQL-Injection proof statement to search for username
            PreparedStatement statement = conn.prepareStatement("select * from Account where username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            // If there's a result, read it.
            if(resultSet.next()) {
                AccountCredential acc = new AccountCredential();
                acc.ReadFromResultSet(resultSet);
                return new AccessResult(true, acc);
            }

            // There's no result.
            return AccessResult.failed();

        });
        return (AccountCredential) result.getResult();
    }

    public boolean checkIfLogInIsValid (String username, String password) {
        AccountCredential acc = checkForAccountWithUsername(username);

        if(acc == null) {
            return false;
        }

        // If the credid. are presents, check if the username + password + salt hashes are the same as the database hash.
        return PasswordHasher.checkHash(acc.getHash(), username, password, acc.getSalt());
    }
}
