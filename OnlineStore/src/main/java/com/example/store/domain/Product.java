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
    @Column
    private Long id;

    @Column
    private String title;

    @Column
    private Integer price;

    @ManyToOne
    @JoinColumn
    private Category category;

    @Column
    private String picturePath;

//    @OneToMany(mappedBy = "product")
//    List<Review> review;
}
