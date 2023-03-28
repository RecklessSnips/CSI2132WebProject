package Entities;

public class CustomerAccount extends Account{
    private int customer_id;
    public CustomerAccount(int customer_id, int account_id, int person_id, String username, int ssn_sin, String creation_date) {
        super(account_id, person_id, username, ssn_sin, creation_date);
        this.customer_id = customer_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    @Override
    public String toString() {
        return "CustomerAccount{" +
                "customer_id=" + customer_id +
                '}';
    }
}
