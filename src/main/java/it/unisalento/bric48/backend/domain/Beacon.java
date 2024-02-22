package it.unisalento.bric48.backend.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("beacons")
public class Beacon {

    @Id
    String id;
    String mac;
    String idMachinery; //mserial

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getMac() {
        return mac;
    }
    public void setMac(String mac) {
        this.mac = mac;
    }
    public String getIdMachinery() {
        return idMachinery;
    }
    public void setIdMachinery(String idMachinery) {
        this.idMachinery = idMachinery;
    }


}
