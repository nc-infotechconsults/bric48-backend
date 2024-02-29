package it.unisalento.bric48.backend.dto;

public class NearbyHeadphonesDTO {

    String id;
    String serial;  //Headphones ID
    String mserial; //Machinery ID
    String idRoom;
    String idBranch;

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
    public String getIdRoom() {
        return idRoom;
    }
    public void setIdRoom(String idRoom) {
        this.idRoom = idRoom;
    }
    public String getIdBranch() {
        return idBranch;
    }
    public void setIdBranch(String idBranch) {
        this.idBranch = idBranch;
    }
    
}
