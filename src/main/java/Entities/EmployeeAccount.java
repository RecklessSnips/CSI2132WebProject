package Entities;

public class EmployeeAccount extends Account{
    private int employee_id;
    public EmployeeAccount(int employee_id, int account_id, int person_id, String username, int ssn_sin, String creation_date) {
        super(account_id, person_id, username, ssn_sin, creation_date);
        this.employee_id = employee_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    @Override
    public String toString() {
        return "EmployeeAccount{" +
                "employee_id=" + employee_id +
                '}';
    }
}
