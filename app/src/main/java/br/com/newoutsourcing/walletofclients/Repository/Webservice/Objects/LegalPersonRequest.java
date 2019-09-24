package br.com.newoutsourcing.walletofclients.Repository.Webservice.Objects;

public class LegalPersonRequest {
    public String SocialName;
    public String FantasyName;
    public String CNPJ;
    public String IE;
    public String IM;

    public String getSocialName() {
        return SocialName;
    }

    public void setSocialName(String socialName) {
        SocialName = socialName;
    }

    public String getFantasyName() {
        return FantasyName;
    }

    public void setFantasyName(String fantasyName) {
        FantasyName = fantasyName;
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
