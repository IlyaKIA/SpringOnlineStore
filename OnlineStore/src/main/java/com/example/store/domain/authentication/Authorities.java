package com.example.store.domain.authentication;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "username")
@Entity
@Table
public class Authorities {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private String username;

    @Column
    private String authority;


}

