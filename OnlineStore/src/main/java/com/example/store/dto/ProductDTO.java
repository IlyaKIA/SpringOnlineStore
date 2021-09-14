package com.example.store.dto;

import com.example.store.domain.Category;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String title;
    private Integer price;
    private String picturePath;
}
