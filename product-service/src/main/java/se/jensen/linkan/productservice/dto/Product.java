package se.jensen.linkan.productservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

    private Long id;
    private String title;
    private Double price;
    private String description;
    private String image;
}