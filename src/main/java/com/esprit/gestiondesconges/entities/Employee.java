package com.esprit.gestiondesconges.entities;

import com.esprit.gestiondesconges.config.token.Token;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
@Builder
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Employee  implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmployee;
    private String nom;
    private String prenom;
    private String email;
    private Date dateNaissance;
    private Date dateRecrutement;
    private String salaire;
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Type type;

    private int soldeConge;
    //////////
    private boolean accountLocked;
    private boolean enabled;


    @OneToMany(mappedBy = "employee")
    private List<Token> tokens;
    //////


    @ManyToOne
    @JoinColumn(name = "equipe_id")
    @JsonBackReference
    private Equipe equipe;

    @ManyToOne
    private Employee manager;

    @OneToMany(mappedBy = "employee")
    private Set<Conge> conges;

    @OneToMany(mappedBy = "employee")
    private Set<Reclamation> reclamations;
    @OneToMany(mappedBy = "employee")
    private Set<ArchivedReclamation> archivedReclamations;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "employee")
    private Set<Historique> historique;

    public void setSoldeConge(int soldeConge) {
        this.soldeConge = soldeConge;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}