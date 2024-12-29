package it.infotechconsults.bric48.backend.domain;

import java.util.Set;

import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "notification_code")
@SQLRestriction(value = "deleted_by is null and deleted_at is null")
public class NotificationCode extends Audit {

    @Column(name = "title")
    private String title;

    @Column(name = "code")
    private String code;
    
    @OneToMany(mappedBy = "notificationCode", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<NotificationTranslation> translations;

}
