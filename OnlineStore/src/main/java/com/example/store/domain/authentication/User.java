package com.example.store.domain.authentication;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "username")
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column
    private String username;

    @Column
    private String password;

    @Column
    private Boolean enabled;

}

