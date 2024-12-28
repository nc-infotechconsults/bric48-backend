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
@Entity(name = "default_translation_message")
public class DefaultTranslationMessage extends BaseEntity {

    @Column(name = "language")
    private String language;

    @Column(name = "is_default")
    private Boolean isDefault = false;

    @Column(name = "message", columnDefinition = "text")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_message_id")
    private DefaultMessage defaultMessage;

}
