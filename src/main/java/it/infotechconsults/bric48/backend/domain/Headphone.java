package it.infotechconsults.bric48.backend.domain;

import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "headphone")
@SQLRestriction(value = "deleted_by is null and deleted_at is null")
public class Headphone extends Audit {

    @Column(name = "name")
    private String name;

    @Column(name = "serial")
    private String serial;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
