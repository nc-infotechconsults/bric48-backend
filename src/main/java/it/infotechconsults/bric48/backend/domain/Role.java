package it.infotechconsults.bric48.backend.domain;

import java.util.Set;

import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "role")
@SQLRestriction(value = "deleted_by is null and deleted_at is null")
public class Role extends Audit {
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "description", columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<User> users;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<RoleFunctionality> functionalities;

}
