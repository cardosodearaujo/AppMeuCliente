package br.com.newoutsourcing.walletofclients.Objects;

import java.io.Serializable;

public class AdditionalInformation implements Serializable {
    private long additionalInformationId;
    private long clientId;
    private String cellphone;
    private String telephone;
    private String email;
    private String site;
    private String observation;
    private boolean success;

    public AdditionalInformation(){
        this.setAdditionalInformationId(0);
        this.setClientId(0);
        this.setCellphone("");
        this.setTelephone("");
        this.setEmail("");
        this.setSite("");
        this.setObservation("");
        this.setSuccess(true);
    }

    public AdditionalInformation(long additionalInformationId, long clientId, String cellphone,
                                 String telephone, String email, String site, String observation, boolean success){
        this.setAdditionalInformationId(additionalInformationId);
        this.setClientId(clientId);
        this.setCellphone(cellphone);
        this.setTelephone(telephone);
        this.setEmail(email);
        this.setSite(site);
        this.setObservation(observation);
        this.setSuccess(success);
    }

    public static AdditionalInformation newInstance(){
        return new AdditionalInformation();
    }

    public long getAdditionalInformationId() {
        return additionalInformationId;
    }

    public void setAdditionalInformationId(long additionalInformationId) {
        this.additionalInformationId = additionalInformationId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
