package model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

@Entity
@Table(name = "Employees")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", nullable = false, length = 64)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 64)
    private String lastName;

    @Column(nullable = false, length = 16)
    private String gender;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dob;

    @Column(name = "email_address", nullable = false, unique = true, length = 64)
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true, length = 16)
    private String phone;

    @Column(nullable = false, length = 512)
    private String password;

    @Column(nullable = false, length = 16)
    private String role;

    @Column(nullable = false, length = 32)
    private String province;

    @Column(nullable = false, length = 32)
    private String district;

    @Column(name = "street_address", nullable = false, length = 64)
    private String streetAddress;

    @Column(name = "postal_code", nullable = false, length = 8)
    private String postalCode;

    @Column(name = "image_path")
    private String imgPath;

}
