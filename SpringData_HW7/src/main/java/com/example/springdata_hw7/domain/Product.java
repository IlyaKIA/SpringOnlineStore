package com.example.springdata_hw7.domain;

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
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private int price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Product(String title, int price) {
        this.title = title;
        this.price = price;
    }

    public Product(String title, int price, String category) {
        this.title = title;
        this.price = price;
        this.category = new Category(category);
    }
}
