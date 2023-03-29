package Entities;

public enum HotelType {
    Default(0),
    Resort(1),
    Motel(2),
    Inn(3),
    Luxury(4);

    // Allows us to convert enums to int back and forth
    private final int value;
    HotelType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
    public boolean Compare(int value){ return this.value == value; }
    public static HotelType getEnum(int value) {
        HotelType[] As = HotelType.values();
        for (int i = 0; i < As.length; i++) {
            if (As[i].Compare(value)) return As[i];
        }
        return HotelType.Default;
    }
}
