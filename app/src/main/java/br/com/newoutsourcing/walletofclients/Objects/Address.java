package br.com.newoutsourcing.walletofclients.Objects;

public class Address {
    private long addressId;
    private String CEP;
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String state;
    private String Country;


    public Address(){
        this.setAddressId(0);
        this.setCEP("");
        this.setStreet("");
        this.setNumber("");
        this.setNeighborhood("");
        this.setCity("");
        this.setState("");
        this.setCountry("");
    }

    public Address(long addressId,String CEP,String street,String number,String neighborhood,String city,String state,String Country){
        this.setAddressId(addressId);
        this.setCEP(CEP);
        this.setStreet(street);
        this.setNumber(number);
        this.setNeighborhood(neighborhood);
        this.setCity(city);
        this.setState(state);
        this.setCountry(Country);
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }
}
