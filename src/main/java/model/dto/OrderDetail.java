package model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class OrderDetail {

    private int itemId;
    private String itemName;
    private int itemQty;
    private double unitPrice;
    private double lineTotal;
}
