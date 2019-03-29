package br.com.newoutsourcing.walletofclients.Objects.Entity;

public class ClientEntity {

    private long clientId;
    private String image;
    private int type;

    public ClientEntity(){
        this.setClientId(0);
        this.setImage("");
        this.setType(0);
    }

    public ClientEntity(long clientId, String image, int type){
        this.setClientId(clientId);
        this.setImage(image);
        this.setType(type);
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
}
