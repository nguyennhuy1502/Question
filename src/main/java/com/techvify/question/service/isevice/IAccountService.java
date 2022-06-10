package com.techvify.question.service.isevice;

import com.techvify.question.DTO.RegistrationAccountDTO;
import com.techvify.question.entity.Account;
import com.techvify.question.entity.VerificationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAccountService{

    List<Account> finAll();

    Account registerAccount(RegistrationAccountDTO registrationAccountDTO);

    void saveVerificationTokenForAccount(String token, Account account);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

    Account findUserByEmail(String email);

    void createPasswordResetTokenForAccount(Account account, String token);

    String validatePasswordResetToken(String token);

    Optional<Account> getUserByPasswordResetToken(String token);

    void changePassword(Account account, String newPassword);

    boolean checkIfValidOldPassword(Account account, String oldPassword);
}
