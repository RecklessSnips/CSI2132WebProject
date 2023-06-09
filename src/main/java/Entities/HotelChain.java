package Entities;

import Utilities.Address;
import Utilities.ISQLReadable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HotelChain implements ISQLReadable {
    private int chainId;
    private String chainName;
    private Address addressCentralOffice;
    private String email;
    private String phone;

    public int getChainId() { return chainId; }
    public String getChainName() { return chainName; }
    public Address getAddressCentralOffice() { return addressCentralOffice; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    public HotelChain () {}

    @Override
    public void ReadFromResultSet(ResultSet resultSet, int startColumn, boolean excludeId) throws SQLException {
        if(!excludeId) {
            startColumn--;
            chainId = resultSet.getInt(1 + startColumn);
        } else {
            startColumn-=2;
        }
        chainName = resultSet.getString(2 + startColumn);
        addressCentralOffice = Address.parseSQLAddress(resultSet.getString(3 + startColumn));
        email = resultSet.getString(4 + startColumn);
        phone = resultSet.getString(5 + startColumn);
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
