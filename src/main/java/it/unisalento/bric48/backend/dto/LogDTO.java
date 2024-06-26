package it.unisalento.bric48.backend.dto;

public class LogDTO {

    String id;
    String log;
    String timestamp;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getLog() {
        return log;
    }
    public void setLog(String log) {
        this.log = log;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
