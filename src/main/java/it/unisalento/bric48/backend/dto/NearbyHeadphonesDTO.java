package it.unisalento.bric48.backend.dto;

public class NearbyHeadphonesDTO {

    String id;
    String serial;  //Headphones ID
    String mserial; //Machinery ID

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getSerial() {
        return serial;
    }
    public void setSerial(String serial) {
        this.serial = serial;
    }
    public String getMserial() {
        return mserial;
    }
    public void setMserial(String mserial) {
        this.mserial = mserial;
    }
    
}
