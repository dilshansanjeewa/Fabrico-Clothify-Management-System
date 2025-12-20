package model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Admin {
    private String email;
    private String password;
    private String fullName;
    private String imgPath;

}
