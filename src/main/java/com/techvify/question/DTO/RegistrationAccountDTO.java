package com.techvify.question.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationAccountDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String matchingPassword;
}
