package com.example.store.domain;

import com.example.store.domain.authentication.UserProfile;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "review_and_rating")
@ToString
@EqualsAndHashCode(exclude = "id, username, product")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name = "username")
    private UserProfile username;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "review")
    private String comment;

    @Column
    private Integer rating;
}
