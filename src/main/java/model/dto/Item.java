package model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class Item {

    private Long id;
    private String name;
    private String category;
    private String subCategory;
    private Brand brand;
    private String size;
    private String color;
    private int qty;
    private double costPrice;
    private Double sellingPrice;
    private String description;
    private String imgPath;
}
