package Databases;

import Entities.Account;
import Entities.AccountCredential;
import Entities.Person;
import Utilities.AccessResult;
import Utilities.PasswordHasher;
import Utilities.Pair;

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
                acc.ReadFromResultSet(resultSet, 1, false);
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

        // If the credentials are presents, check if the username + password + salt hashes are the same as the database hash.
        return PasswordHasher.checkHash(acc.getHash(), username, password, acc.getSalt());
    }

    public int getAccountIdFromUsername (String username) {
        AccessResult result = tryReturnStatement((conn) -> {

            PreparedStatement statement = conn.prepareStatement("select account_id from Account where username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            // If there's a result, read it.
            if(resultSet.next()) {
                return new AccessResult(true, resultSet.getInt(1));
            } else {
                return AccessResult.failed();
            }
        });

        if(result.didSucceed()) {
            return (int)result.getResult();
        } else {
            return 0;
        }
    }

    public Pair<Account, Person> getAccountPersonPair (int accountId) {
        AccessResult result = tryReturnStatement((conn) -> {

            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Account NATURAL JOIN Person WHERE account_id = ?");
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();

            // If there's a result, read it.
            if(resultSet.next()) {
                Account account = new Account();
                Person person = new Person();

                // In this case, to read the result, we need to read the account first, then person with an startColumn of 9 and index disabled
                account.ReadFromResultSet(resultSet, 1, false);
                person.ReadFromResultSet(resultSet, 9, true);
                person.setPersonId(account.getPersonId());

                return new AccessResult(true, new Pair<Account, Person>(account, person));
            } else {
                return AccessResult.failed();
            }
        });
        return (Pair<Account, Person>) result.getResult();
    }

    public int createNewAccount(Account account, Person person, AccountCredential accountCredential) {
        AccessResult result = tryReturnStatement((conn) -> {
            int personId = person.WriteFromStatement(conn);
            if(personId == 0) return AccessResult.failed();

            account.setPersonId(personId);
            int accountId = account.WriteFromStatement(conn);
            if(accountId == 0) return AccessResult.failed();

            accountCredential.setAccountId(accountId);
            accountCredential.UpdateFromStatement(conn);

            return new AccessResult(true, accountId);
        });

        if(result.didSucceed()) {
            return (int)result.getResult();
        } else {
            return 0;
        }
    }

    public void deleteAccount (int accountId) {
        tryRunStatement((conn) -> {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM Account WHERE account_id = ?");
            statement.setInt(1, accountId);
            return statement.executeQuery().next();
        });

    }
}
