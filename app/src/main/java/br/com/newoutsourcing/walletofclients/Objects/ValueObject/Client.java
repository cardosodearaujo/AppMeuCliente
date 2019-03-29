package br.com.newoutsourcing.walletofclients.Objects.ValueObject;

import br.com.newoutsourcing.walletofclients.Objects.Entity.AdditionalInformationEntity;
import br.com.newoutsourcing.walletofclients.Objects.Entity.AddressEntity;
import br.com.newoutsourcing.walletofclients.Objects.Entity.ClientEntity;
import br.com.newoutsourcing.walletofclients.Objects.Entity.LegalPersonEntity;
import br.com.newoutsourcing.walletofclients.Objects.Entity.PhysicalPersonEntity;

public class Client extends ClientEntity {

    private PhysicalPersonEntity physicalPerson;
    private LegalPersonEntity legalPerson;
    private AdditionalInformationEntity additionalInformation;
    private AddressEntity addressEntity;

    public Client(){
        super();
        this.physicalPerson = PhysicalPersonEntity.newInstance();
        this.legalPerson = LegalPersonEntity.newInstance();
        this.additionalInformation = AdditionalInformationEntity.newInstance();
        this.addressEntity = AddressEntity.newInstance();
    }

    public Client(long clientId, String image, int type, PhysicalPersonEntity physicalPerson, LegalPersonEntity legalPerson,
                  AdditionalInformationEntity additionalInformation, AddressEntity addressEntity){
        super(clientId,image,type);
        this.physicalPerson = physicalPerson;
        this.legalPerson = legalPerson;
        this.additionalInformation = additionalInformation;
        this.addressEntity = addressEntity;
    }

    public PhysicalPersonEntity getPhysicalPerson() {
        return physicalPerson;
    }

    public void setPhysicalPerson(PhysicalPersonEntity physicalPerson) {
        this.physicalPerson = physicalPerson;
    }

    public LegalPersonEntity getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(LegalPersonEntity legalPerson) {
        this.legalPerson = legalPerson;
    }

    public AdditionalInformationEntity getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(AdditionalInformationEntity additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public AddressEntity getAddressEntity() {
        return addressEntity;
    }

    public void setAddressEntity(AddressEntity addressEntity) {
        this.addressEntity = addressEntity;
    }
}
