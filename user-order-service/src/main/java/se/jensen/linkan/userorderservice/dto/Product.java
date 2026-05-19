package se.jensen.linkan.userorderservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
}