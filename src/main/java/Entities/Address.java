package Entities;

public class Address {
    private boolean isValid;
    private int streetNumber;
    private String streetName;
    private String city;
    private String region;
    private String country;

    public boolean isValid() {
        return isValid;
    }
    public int getStreetNumber() {
        return streetNumber;
    }
    public String getStreetName() {
        return streetName;
    }
    public String getCity() {
        return city;
    }
    public String getRegion() {
        return region;
    }
    public String getCountry() {
        return country;
    }

    public static Address parseSQLAddress (String sqlAddress) {
        Address add = new Address();

        String[] segments = sqlAddress.split(",");
        if(segments.length != 5) {
            add.isValid = false;
            return add;
        }

        add.streetNumber = Integer.parseInt(segments[0].trim());
        add.streetName = segments[1].trim();
        add.city = segments[2].trim();
        add.region = segments[3].trim();
        add.country = segments[4].trim();

        return add;
    }

}
