package br.com.newoutsourcing.walletofclients.Objects.Entity;

public class LegalPersonEntity {
    private long legalPersonId;
    private long clientId; //Chave estrangeira
    private String socialName;
    private String fantasyName;
    private String CNPJ;
    private String IE;
    private String IM;

    public LegalPersonEntity(){
        this.setLegalPersonId(0);
        this.setClientId(0);
        this.setSocialName("");
        this.setFantasyName("");
        this.setCNPJ("");
        this.setIE("");
        this.setIM("");
    }

    public LegalPersonEntity(long legalPersonId, long clientId, String socialName, String fantasyName, String CNPJ, String IE, String IM){
        this.setLegalPersonId(legalPersonId);
        this.setClientId(clientId);
        this.setSocialName(socialName);
        this.setFantasyName(fantasyName);
        this.setCNPJ(CNPJ);
        this.setIE(IE);
        this.setIM(IM);
    }

    public static LegalPersonEntity newInstance(){
        return new LegalPersonEntity();
    }

    public long getLegalPersonId() {
        return legalPersonId;
    }

    public void setLegalPersonId(long legalPersonId) {
        this.legalPersonId = legalPersonId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
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
