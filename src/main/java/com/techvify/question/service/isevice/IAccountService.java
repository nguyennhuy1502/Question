package com.techvify.question.service.isevice;

import com.techvify.question.DTO.RegistrationAccountDTO;
import com.techvify.question.entity.Account;

import java.util.List;

public interface IAccountService {

    List<Account> finAll();

    Account registerAccount(RegistrationAccountDTO registrationAccountDTO);

    void saveVerificationTokenForAccount(String token, Account account);
}
