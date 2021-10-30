package com.example.store.domain.authentication;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table
public class UserProfile {

    public UserProfile(String username, String picturePath) {
        this.username = username;
        this.picturePath = picturePath;
    }

    @Id
    private String username;

    private String name;

    private String email;

    private String phoneNumber;

    private String city;

    private String picturePath;
}
