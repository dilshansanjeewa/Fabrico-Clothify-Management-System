package model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class Item {
    private String name;
    private String category;
    private String subCategory;
    private String brand;
    private String size;
    private String color;
    private int qty;
    private double costPrice;
    private Double sellingPrice;
    private String description;
}
