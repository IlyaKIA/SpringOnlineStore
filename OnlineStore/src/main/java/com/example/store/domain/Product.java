package com.example.store.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @JsonBackReference
    @OneToMany(mappedBy = "product")
    List<Review> review;
}
