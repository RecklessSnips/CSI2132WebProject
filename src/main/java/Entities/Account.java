package Entities;

public class Account {
    private int account_id;
    private int person_id;
    private String username;
    private int ssn_sin;
    private String creation_date;

    public Account(int account_id, int person_id, String username, int ssn_sin, String creation_date) {
        this.account_id = account_id;
        this.person_id = person_id;
        this.username = username;
        this.ssn_sin = ssn_sin;
        this.creation_date = creation_date;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSsn_sin() {
        return ssn_sin;
    }

    public void setSsn_sin(int ssn_sin) {
        this.ssn_sin = ssn_sin;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    @Override
    public String toString() {
        return "Account{" +
                "account_id=" + account_id +
                ", person_id=" + person_id +
                ", username='" + username + '\'' +
                ", ssn_sin=" + ssn_sin +
                ", creation_date='" + creation_date + '\'' +
                '}';
    }
}
