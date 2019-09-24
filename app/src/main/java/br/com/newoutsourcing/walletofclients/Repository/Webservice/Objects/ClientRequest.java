package br.com.newoutsourcing.walletofclients.Repository.Webservice.Objects;

import br.com.newoutsourcing.walletofclients.Objects.Client;

public class ClientRequest {
    private long Codigo;
    private String Image;
    private long Type;
    private PhysicalPersonRequest PhysicalPerson;
    private LegalPersonRequest LegalPerson;
    private String Cellphone;
    private String Telephone;
    private String Email;
    private String Site;
    private String Observation;
    private String CEP;
    private String Street;
    private long Number;
    private String Neighborhood;
    private String City;
    private String State;
    private String Country;

    public long getCodigo() {
        return Codigo;
    }

    public void setCodigo(long codigo) {
        Codigo = codigo;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public long getType() {
        return Type;
    }

    public void setType(long type) {
        Type = type;
    }

    public PhysicalPersonRequest getPhysicalPerson() {
        return PhysicalPerson;
    }

    public void setPhysicalPerson(PhysicalPersonRequest physicalPerson) {
        PhysicalPerson = physicalPerson;
    }

    public LegalPersonRequest getLegalPerson() {
        return LegalPerson;
    }

    public void setLegalPerson(LegalPersonRequest legalPerson) {
        LegalPerson = legalPerson;
    }

    public String getCellphone() {
        return Cellphone;
    }

    public void setCellphone(String cellphone) {
        Cellphone = cellphone;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSite() {
        return Site;
    }

    public void setSite(String site) {
        Site = site;
    }

    public String getObservation() {
        return Observation;
    }

    public void setObservation(String observation) {
        Observation = observation;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public long getNumber() {
        return Number;
    }

    public void setNumber(long number) {
        Number = number;
    }

    public String getNeighborhood() {
        return Neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        Neighborhood = neighborhood;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public ClientRequest convert(Client c){
        ClientRequest client = new ClientRequest();

        if (c.getIdNuvem() > 0) client.setCodigo(c.getIdNuvem());
        client.setImage(c.getImage());
        client.setType(c.getType());
        client.setCellphone(c.getAdditionalInformation().getCellphone());
        client.setTelephone(c.getAdditionalInformation().getTelephone());
        client.setEmail(c.getAdditionalInformation().getEmail());
        client.setSite(c.getAdditionalInformation().getSite());
        client.setObservation(c.getAdditionalInformation().getObservation());
        client.setCEP(c.getAddress().getCEP());
        client.setStreet(c.getAddress().getStreet());
        client.setNumber(c.getAddress().getNumber());
        client.setNeighborhood(c.getAddress().getNeighborhood());
        client.setCity(c.getAddress().getCity());
        client.setState(c.getAddress().getState());
        client.setCountry(c.getAddress().getCountry());

        if (c.getType() == 1){
            PhysicalPersonRequest physicalPerson = new PhysicalPersonRequest();
            physicalPerson.setName(c.getPhysicalPerson().getName());
            physicalPerson.setNickname(c.getPhysicalPerson().getNickname());
            physicalPerson.setCPF(c.getPhysicalPerson().getCPF());
            physicalPerson.setRG(c.getPhysicalPerson().getRG());
            physicalPerson.setBirthday(c.getPhysicalPerson().getBirthDate());
            physicalPerson.setSex(c.getPhysicalPerson().getSex());
            client.setPhysicalPerson(physicalPerson);
        }else{
            LegalPersonRequest legalPerson = new LegalPersonRequest();
            legalPerson.setSocialName(c.getPhysicalPerson().getName());
            legalPerson.setFantasyName(c.getPhysicalPerson().getNickname());
            legalPerson.setCNPJ(c.getPhysicalPerson().getCPF());
            legalPerson.setIE(c.getPhysicalPerson().getRG());
            legalPerson.setIM(c.getPhysicalPerson().getBirthDate());
            client.setLegalPerson(legalPerson);
        }

        return client;
    }
}
