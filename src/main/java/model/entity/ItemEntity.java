package model.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

@Entity
@Table(name = "item")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String category;

    @Column(name = "sub_category")
    private String subCategory;
    private String size;
    private String color;

    @Column(name = "quantity")
    private int qty;

    @Column(name = "cost_price")
    private double costPrice;

    @Column(name = "selling_price")
    private double sellingPrice;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_path")
    private String imgPath;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private BrandEntity brand;
}
