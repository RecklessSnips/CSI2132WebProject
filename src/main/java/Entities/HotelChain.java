package Entities;

public class HotelChain {
    private int chain_id;
    private String chain_name;
    private String address_central_office;
    private String email;
    private String phone;

    public HotelChain(int chain_id, String chain_name, String address_central_office, String email, String phone) {
        this.chain_id = chain_id;
        this.chain_name = chain_name;
        this.address_central_office = address_central_office;
        this.email = email;
        this.phone = phone;
    }

    public int getChain_id() {
        return chain_id;
    }

    public void setChain_id(int chain_id) {
        this.chain_id = chain_id;
    }

    public String getChain_name() {
        return chain_name;
    }

    public void setChain_name(String chain_name) {
        this.chain_name = chain_name;
    }

    public String getAddress_central_office() {
        return address_central_office;
    }

    public void setAddress_central_office(String address_central_office) {
        this.address_central_office = address_central_office;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "HotelChain{" +
                "chain_id=" + chain_id +
                ", chain_name='" + chain_name + '\'' +
                ", address_central_office='" + address_central_office + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
