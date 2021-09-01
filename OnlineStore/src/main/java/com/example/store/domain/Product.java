package com.example.store.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
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

//    public Product(String title, int price) {
//        this.title = title;
//        this.price = price;
//    }

//    public Product(String title, int price, String category) {
//        this.title = title;
//        this.price = price;
//        this.category = new Category(category);
//    }
}
