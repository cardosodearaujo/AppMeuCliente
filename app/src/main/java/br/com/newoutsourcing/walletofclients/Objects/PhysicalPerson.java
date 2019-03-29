package br.com.newoutsourcing.walletofclients.Objects;

import br.com.newoutsourcing.walletofclients.App.FunctionsApp;

public class PhysicalPerson {
    private long physicalPersonId;
    private String name;
    private String apelido;
    private String CPF;
    private String RG;
    private String birthDate;
    private String sex;

    public PhysicalPerson(){
        this.setPhysicalPersonId(0);
        this.setName("");
        this.setApelido("");
        this.setCPF("");
        this.setRG("");
        this.setBirthDate(FunctionsApp.getCurrentDate());
        this.setSex("");
    }

    public PhysicalPerson(long physicalPersonId,String name,String apelido,
                          String CPF,String RG,String birthDate,String sex){
        this.setPhysicalPersonId(physicalPersonId);
        this.setName(name);
        this.setApelido(apelido);
        this.setCPF(CPF);
        this.setRG(RG);
        this.setBirthDate(birthDate);
        this.setSex(sex);
    }

    public long getPhysicalPersonId() {
        return physicalPersonId;
    }

    public void setPhysicalPersonId(long physicalPersonId) {
        this.physicalPersonId = physicalPersonId;
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
