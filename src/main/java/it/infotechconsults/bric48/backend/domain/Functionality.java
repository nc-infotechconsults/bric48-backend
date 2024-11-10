package it.infotechconsults.bric48.backend.domain;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "functionality")
public class Functionality {
   
    @Id
    private String id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "description")
    private String description;

    @Column(name = "code")
    private String code;

    @OneToMany(mappedBy = "functionality")
    private Set<RoleFunctionality> roles;

}
