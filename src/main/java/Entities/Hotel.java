package Entities;

public class Hotel {

    private int hotel_id;
    private int chain_id;
    private int manager_id;
    private String address;
    private String phone;
    private String rating;
    private int room_count;

    private HotelChain hotelChain;

    public Hotel(int hotel_id, HotelChain hotelChain, int manager_id, String address, String phone, String rating, int room_count) {
        this.hotel_id = hotel_id;
        this.chain_id = hotelChain.getChain_id();
        this.manager_id = manager_id;
        this.address = address;
        this.phone = phone;
        this.rating = rating;
        this.room_count = room_count;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public int getChain_id() {
        return chain_id;
    }

//    public void setChain_id(int chain_id) {
//        this.chain_id = chain_id;
//    }

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getRoom_count() {
        return room_count;
    }

    public void setRoom_count(int room_count) {
        this.room_count = room_count;
    }
    public String getChain_name() {
        return "This hotel belongs to: " + hotelChain.getChain_name();
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hotel_id=" + hotel_id +
                ", chain_id=" + chain_id +
                ", manager_id=" + manager_id +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", rating='" + rating + '\'' +
                ", room_count=" + room_count +
                '}';
    }
}
