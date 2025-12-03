package model.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Employee {
    private Long id;
    private String visibleId;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate dob;
    private String email;
    private String phone;
    private String province;
    private String district;
    private String streetAddress;
    private String postalCode;
    private String imgPath;
}
