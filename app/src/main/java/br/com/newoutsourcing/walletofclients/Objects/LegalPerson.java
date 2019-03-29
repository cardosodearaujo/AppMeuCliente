package br.com.newoutsourcing.walletofclients.Objects;

public class LegalPerson {
    private long legalPersonId;
    private String socialName;
    private String fantasyName;
    private String CNPJ;
    private String IE;
    private String IM;

    public LegalPerson(){
        this.setLegalPersonId(0);
        this.setSocialName("");
        this.setFantasyName("");
        this.setCNPJ("");
        this.setIE("");
        this.setIM("");
    }

    public LegalPerson(long legalPersonId,String socialName,String fantasyName,String CNPJ,String IE,String IM){
        this.setLegalPersonId(legalPersonId);
        this.setSocialName(socialName);
        this.setFantasyName(fantasyName);
        this.setCNPJ(CNPJ);
        this.setIE(IE);
        this.setIM(IM);
    }

    public long getLegalPersonId() {
        return legalPersonId;
    }

    public void setLegalPersonId(long legalPersonId) {
        this.legalPersonId = legalPersonId;
    }

    public String getSocialName() {
        return socialName;
    }

    public void setSocialName(String socialName) {
        this.socialName = socialName;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getIE() {
        return IE;
    }

    public void setIE(String IE) {
        this.IE = IE;
    }

    public String getIM() {
        return IM;
    }

    public void setIM(String IM) {
        this.IM = IM;
    }
}
