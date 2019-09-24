package br.com.newoutsourcing.walletofclients.Objects;

import java.io.Serializable;

public class Client implements Serializable {

    private long clientId;
    private String image;
    private int type;
    private PhysicalPerson physicalPerson;
    private LegalPerson legalPerson;
    private AdditionalInformation additionalInformation;
    private Address address;
    private boolean success;
    private long idNuvem;
    private String update;

    public Client(){
        this.setClientId(0);
        this.setImage("");
        this.setType(0);
        this.setPhysicalPerson(PhysicalPerson.newInstance());
        this.setLegalPerson(LegalPerson.newInstance());
        this.setAdditionalInformation(AdditionalInformation.newInstance());
        this.setAddress(Address.newInstance());
        this.setSuccess(true);
        this.setIdNuvem(0);
        this.setUpdate("N");
    }

    public Client(long clientId, String image, int type, PhysicalPerson physicalPerson, LegalPerson legalPerson,
                  AdditionalInformation additionalInformation, Address addressEntity, boolean success, long idNuvem, String update){
        this.setClientId(clientId);
        this.setImage(image);
        this.setType(type);
        this.setPhysicalPerson(physicalPerson);
        this.setLegalPerson(legalPerson);
        this.setAdditionalInformation(additionalInformation);
        this.setAddress(addressEntity);
        this.setSuccess(success);
        this.setIdNuvem(idNuvem);
        this.setUpdate(update);
    }

    public static Client newInstance(){
        return new Client();
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public PhysicalPerson getPhysicalPerson() {
        return physicalPerson;
    }

    public void setPhysicalPerson(PhysicalPerson physicalPerson) {
        this.physicalPerson = physicalPerson;
    }

    public LegalPerson getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(LegalPerson legalPerson) {
        this.legalPerson = legalPerson;
    }

    public AdditionalInformation getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(AdditionalInformation additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getIdNuvem() {
        return idNuvem;
    }

    public void setIdNuvem(long idNuvem) {
        this.idNuvem = idNuvem;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
}
