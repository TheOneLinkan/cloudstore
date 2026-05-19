package se.jensen.linkan.userorderservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
public class ProductSnapshot {

    @Id
    private Long id;

    private String title;

    private Double price;
}