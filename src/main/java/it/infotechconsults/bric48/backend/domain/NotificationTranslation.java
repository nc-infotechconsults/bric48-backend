package it.infotechconsults.bric48.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "notification_translation")
public class NotificationTranslation extends BaseEntity {

    @Column(name = "language")
    private String language;

    @Column(name = "is_default")
    private Boolean isDefault;

    @Column(name = "message", columnDefinition = "text")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_code_id")
    private NotificationCode notificationCode;

}
