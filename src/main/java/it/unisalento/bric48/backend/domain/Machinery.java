package it.unisalento.bric48.backend.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("machineries")
public class Machinery {
    
    @Id
    String id;
    String mserial;
    String name;
    String topic;
    String idRoom;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getMserial() {
        return mserial;
    }
    public void setMserial(String mserial) {
        this.mserial = mserial;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIdRoom() {
        return idRoom;
    }
    public void setIdRoom(String idRoom) {
        this.idRoom = idRoom;
    }
    public String getTopic() {
        return topic;
    }
    public void setTopic(String topic) {
        this.topic = topic;
    }


}
