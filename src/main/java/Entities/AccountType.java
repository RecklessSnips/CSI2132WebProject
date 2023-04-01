package Entities;

public enum AccountType {
    Client(0),
    Employee(1),
    Admin(2);

    // Allows us to convert enums to int back and forth
    private final int value;
    AccountType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
    public boolean Compare(int value){ return this.value == value; }
    public static AccountType getEnum(int value) {
        AccountType[] As = AccountType.values();
        for (int i = 0; i < As.length; i++) {
            if (As[i].Compare(value)) return As[i];
        }
        return AccountType.Client;
    }
}
