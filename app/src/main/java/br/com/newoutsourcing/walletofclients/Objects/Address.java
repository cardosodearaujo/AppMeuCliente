package br.com.newoutsourcing.walletofclients.Objects;

public class Address {
    private long addressId;
    private long clientId;
    private String CEP;
    private String street;
    private int number;
    private String neighborhood;
    private String city;
    private String state;
    private String country;
    private boolean success;

    public Address(){
        this.setAddressId(0);
        this.setClientId(0);
        this.setCEP("");
        this.setStreet("");
        this.setNumber(0);
        this.setNeighborhood("");
        this.setCity("");
        this.setState("");
        this.setCountry("");
        this.setSuccess(true);
    }

    public Address(long addressId, long clientId , String CEP, String street, int number,
                   String neighborhood, String city, String state, String Country, boolean success){
        this.setAddressId(addressId);
        this.setClientId(clientId);
        this.setCEP(CEP);
        this.setStreet(street);
        this.setNumber(0);
        this.setNeighborhood(neighborhood);
        this.setCity(city);
        this.setState(state);
        this.setCountry(Country);
        this.setSuccess(success);
    }

    public static Address newInstance(){
        return new Address();
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
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
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
