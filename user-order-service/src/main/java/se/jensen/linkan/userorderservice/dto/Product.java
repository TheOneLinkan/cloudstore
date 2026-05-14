package se.jensen.linkan.userorderservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Product {

    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
}