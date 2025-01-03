package it.infotechconsults.bric48.backend.domain;

import java.util.Set;

import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "user")
@SQLRestriction(value = "deleted_by is null and deleted_at is null")
public class User extends Audit {

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "language")
    private String language;

    @Column(name = "reg_number")
    private String regNumber;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "password")
    @JsonIgnore 
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private Headphone headphone;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_machinery", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "machinery_id"))
    private Set<Machinery> machineries;

}
