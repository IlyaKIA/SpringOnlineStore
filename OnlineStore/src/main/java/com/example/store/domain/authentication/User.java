package com.example.store.domain.authentication;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Entity
@Table(name = "users")
public class User {

    @Id
    private String username;

    private String password;

    private Boolean enabled;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "username", referencedColumnName = "username")
    private UserProfile userProfile;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "username", referencedColumnName = "username")
    private Authorities authorities;

}

