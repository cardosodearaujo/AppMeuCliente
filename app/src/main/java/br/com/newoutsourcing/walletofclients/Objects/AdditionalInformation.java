package br.com.newoutsourcing.walletofclients.Objects;

public class AdditionalInformation {
    private long additionalInformationId;
    private String cellphone;
    private String telephone;
    private String email;
    private String site;
    private String observation;

    public AdditionalInformation(){
        this.setAdditionalInformationId(0);
        this.setCellphone("");
        this.setTelephone("");
        this.setEmail("");
        this.setSite("");
        this.setObservation("");
    }

    public AdditionalInformation(long additionalInformationId,String cellphone, String telephone, String email, String site, String observation){
        this.setAdditionalInformationId(additionalInformationId);
        this.setCellphone(cellphone);
        this.setTelephone(telephone);
        this.setEmail(email);
        this.setSite(site);
        this.setObservation(observation);
    }


    public long getAdditionalInformationId() {
        return additionalInformationId;
    }

    public void setAdditionalInformationId(long additionalInformationId) {
        this.additionalInformationId = additionalInformationId;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
