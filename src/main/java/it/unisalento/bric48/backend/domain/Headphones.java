package it.unisalento.bric48.backend.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("headphones")
public class Headphones {

    @Id
    String id;
    String serial;
    String isAssociated; //True or False

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

    public String getIsAssociated() {
        return isAssociated;
    }

    public void setIsAssociated(String isAssociated) {
        this.isAssociated = isAssociated;
    }
    
    
}
