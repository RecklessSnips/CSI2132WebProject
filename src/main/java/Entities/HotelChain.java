package Entities;

public class HotelChain {
    private int chainId;
    private String chainName;
    private Address addressCentralOffice;
    private String email;
    private String phone;

    public int getChain_id() {
        return chainId;
    }
    public String getChain_name() {
        return chainName;
    }
    public Address getAddress_central_office() {
        return addressCentralOffice;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }

    public HotelChain(int chainId, String chainName, Address addressCentralOffice, String email, String phone) {
        this.chainId = chainId;
        this.chainName = chainName;
        this.addressCentralOffice = addressCentralOffice;
        this.email = email;
        this.phone = phone;
    }


    @Override
    public String toString() {
        return "HotelChain{" +
                "chain_id=" + chainId +
                ", chain_name='" + chainName + '\'' +
                ", address_central_office='" + addressCentralOffice + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
