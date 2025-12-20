package model.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class Order {
    private String orderCode;
    private LocalDate orderDate;
    private double totalAmount;

    private List<OrderDetail> orderDetails;
}
