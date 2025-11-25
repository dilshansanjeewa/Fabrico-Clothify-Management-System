package model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Supplier {
    private String name;
    private String email;
    private String phone;
    private String address;
}
