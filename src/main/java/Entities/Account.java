package Entities;

public class Account {
    private int accountId;
    private int personId;
    private String username;
    private int ssnSin;
    private String creationDate;
    private AccountType type;

    public int getAccountId() {
        return accountId;
    }
    public int getPerson_id() {
        return personId;
    }
    public String getUsername() {
        return username;
    }
    public int getSsnSin() {
        return ssnSin;
    }
    public String getCreationDate() {
        return creationDate;
    }

    public Account(int accountId, int personId, AccountType type, String username, int ssnSin, String creationDate) {
        this.accountId = accountId;
        this.personId = personId;
        this.type = type;
        this.username = username;
        this.ssnSin = ssnSin;
        this.creationDate = creationDate;
    }


    @Override
    public String toString() {
        return "Account{" +
                "account_id=" + accountId +
                ", person_id=" + personId +
                ", username='" + username + '\'' +
                ", ssn_sin=" + ssnSin +
                ", creation_date='" + creationDate + '\'' +
                '}';
    }
}
