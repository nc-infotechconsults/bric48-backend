package it.unisalento.bric48.backend.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("rooms")
public class Room {
    
    @Id
    String id;
    String name;
    String idBranch;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIdBranch() {
        return idBranch;
    }
    public void setIdBranch(String idBranch) {
        this.idBranch = idBranch;
    }

    
}
