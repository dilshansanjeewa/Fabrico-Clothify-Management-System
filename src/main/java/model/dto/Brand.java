package model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Brand {
    private Long id;
    private String name;
    private Supplier supplier;
}
