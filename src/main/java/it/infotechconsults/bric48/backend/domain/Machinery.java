package it.infotechconsults.bric48.backend.domain;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "machinery")
public class Machinery extends Audit {

    @Column(name = "name")
    private String name;

    @Column(name = "serial")
    private String serial;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "machinery")
    private Set<Beacon> beacons;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area area;

}
