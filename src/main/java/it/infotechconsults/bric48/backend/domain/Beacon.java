package it.infotechconsults.bric48.backend.domain;

import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "beacon")
@SQLRestriction(value = "deleted_by is null and deleted_at is null")
public class Beacon extends Audit {

    @Column(name = "name")
    private String name;

    @Column(name = "serial")
    private String serial;

    @Column(name = "threshold")
    private Float threshold;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machinery_id")
    private Machinery machinery;

}
