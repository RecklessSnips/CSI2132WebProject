package Utilities;

public class Address {
    private boolean isValid;
    private int streetNumber;
    private String streetName;
    private String unit;
    private String city;
    private String region;
    private String country;

    public boolean isValid() { return isValid; }
    public int getStreetNumber() { return streetNumber; }
    public String getStreetName() { return streetName; }
    public String getCity() { return city; }
    public String getRegion() { return region; }
    public String getCountry() { return country; }

    public static Address parseSQLAddress (String sqlAddress) {
        Address add = new Address();

        String[] segments = sqlAddress.split(",");
        if(segments.length < 4) {
            add.isValid = false;
            return add;
        }

        int firstSpaceInStreet = segments[0].indexOf(' ');
        String streetName = segments[0].substring(firstSpaceInStreet + 1);

        if(segments.length == 4) {
            // No units
            add.streetNumber = Integer.parseInt(segments[0].split(" ")[0].trim());
            add.streetName = streetName;
            add.city = segments[1].trim();
            add.region = segments[2].trim();
            add.country = segments[3].trim();
        }
        else {
            // Units are stored
            add.streetNumber = Integer.parseInt(segments[0].split(" ")[0].trim());
            add.streetName = streetName;
            add.unit = segments[1].trim();
            add.city = segments[2].trim();
            add.region = segments[3].trim();
            add.country = segments[4].trim();
        }

        return add;
    }

    public String toString () {
        String address = streetName.toString() + " " + streetName + ", ";
        if(unit != null) {
            address += unit + ", ";
        }
        address += city + ", ";
        address += region + ", ";
        address += country;
        return address;
    }
}
