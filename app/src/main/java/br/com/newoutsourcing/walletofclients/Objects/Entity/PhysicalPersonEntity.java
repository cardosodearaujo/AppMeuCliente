package br.com.newoutsourcing.walletofclients.Objects.Entity;

import br.com.newoutsourcing.walletofclients.App.FunctionsApp;

public class PhysicalPersonEntity {
    private long physicalPersonId;
    private long clientId; //Chave estrangeira
    private String name;
    private String apelido;
    private String CPF;
    private String RG;
    private String birthDate;
    private String sex;

    public PhysicalPersonEntity(){
        this.setPhysicalPersonId(0);
        this.setClientId(0);
        this.setName("");
        this.setApelido("");
        this.setCPF("");
        this.setRG("");
        this.setBirthDate(FunctionsApp.getCurrentDate());
        this.setSex("");
    }

    public PhysicalPersonEntity(long physicalPersonId, long clientId, String name, String apelido,
                                String CPF, String RG, String birthDate, String sex){
        this.setPhysicalPersonId(physicalPersonId);
        this.setClientId(clientId);
        this.setName(name);
        this.setApelido(apelido);
        this.setCPF(CPF);
        this.setRG(RG);
        this.setBirthDate(birthDate);
        this.setSex(sex);
    }

    public static PhysicalPersonEntity newInstance(){
        return new PhysicalPersonEntity();
    }

    public long getPhysicalPersonId() {
        return physicalPersonId;
    }

    public void setPhysicalPersonId(long physicalPersonId) {
        this.physicalPersonId = physicalPersonId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getRG() {
        return RG;
    }

    public void setRG(String RG) {
        this.RG = RG;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
