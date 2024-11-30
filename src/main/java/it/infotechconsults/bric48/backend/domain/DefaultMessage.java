package it.infotechconsults.bric48.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "default_message")
public class DefaultMessage extends Audit {

    @Column(name = "title")
    private String title;

    @Column(name = "message", columnDefinition = "text")
    private String message;

}
