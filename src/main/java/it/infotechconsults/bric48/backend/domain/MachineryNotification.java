package it.infotechconsults.bric48.backend.domain;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "machinery_notification")
public class MachineryNotification extends BaseEntity {

    @Column(name = "type")
    private String type;

    @Column(name = "value")
    private String value;

    @Column(name = "description")
    private String description;

    @Column(name = "solved")
    private Boolean solved;

    @Column(name = "created_at")
    private Instant createdAt;

    @OneToOne(mappedBy = "notification")
    private Message message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machinery_id")
    private Machinery machinery;

}
