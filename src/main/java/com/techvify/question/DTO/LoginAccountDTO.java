package com.techvify.question.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginAccountDTO {
    private String username;
    private String password;

    public LoginAccountDTO() {

    }

    public LoginAccountDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
