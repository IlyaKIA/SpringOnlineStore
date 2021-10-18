package com.example.store.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "review_and_rating")
@ToString
@EqualsAndHashCode(exclude = "id")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

//    @ManyToOne
//    @JoinColumn
    @Column
    private String username;

    @ManyToOne
    @JoinColumn
    private Product product;

    @Column
    private String review;

    @Column
    private Integer rating;
}
