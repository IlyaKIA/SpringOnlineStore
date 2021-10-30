package com.example.store.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
@ToString(exclude = "category")
@EqualsAndHashCode(exclude = {"id", "category"})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Integer price;

    @ManyToOne
    @JoinColumn
    private Category category;

    private String picturePath;
}
