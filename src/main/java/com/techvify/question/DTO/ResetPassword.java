package com.techvify.question.DTO;

import lombok.Data;

@Data
public class ResetPassword {
    private String email;
    private String oldPassword;
    private String newPassword;
}
