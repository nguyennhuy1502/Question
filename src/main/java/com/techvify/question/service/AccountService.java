package com.techvify.question.service;

import com.techvify.question.DTO.RegistrationAccountDTO;
import com.techvify.question.entity.Account;
import com.techvify.question.entity.PasswordResetToken;
import com.techvify.question.entity.VerificationToken;
import com.techvify.question.repository.IAccountRepository;
import com.techvify.question.repository.IPasswordResetTokenRepository;
import com.techvify.question.repository.IVerificationTokenRepository;
import com.techvify.question.service.isevice.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private IAccountRepository repository;

    @Autowired
    private IVerificationTokenRepository verificationTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IPasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public List<Account> finAll() {
        return repository.findAll();
    }

    @Override
    public Account registerAccount(RegistrationAccountDTO registrationAccountDTO) {
        Account account = new Account();
        account.setEmail(registrationAccountDTO.getEmail());
        account.setFirstName(registrationAccountDTO.getFirstName());
        account.setLastName(registrationAccountDTO.getLastName());
        account.setUsername(registrationAccountDTO.getUsername());
        account.setPassword(passwordEncoder.encode(registrationAccountDTO.getPassword()));
        account.setRole("USER");

        repository.save(account);
        return account;
    }

    @Override
    public void saveVerificationTokenForAccount(String token, Account account) {
        VerificationToken verificationToken = new VerificationToken(account, token);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            return "invalid";

        }
        Account account = verificationToken.getAccount();
        Calendar calendar = Calendar.getInstance();
        if ((verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }
        account.setEnabled(true);
        repository.save(account);
        return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public Account findUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public void createPasswordResetTokenForAccount(Account account, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(account, token);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null) {
            return "invalid";

        }
        Account account = passwordResetToken.getAccount();
        Calendar calendar = Calendar.getInstance();
        if ((passwordResetToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return "expired";
        }

        return "valid";
    }

    @Override
    public Optional<Account> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getAccount());
    }

    @Override
    public void changePassword(Account account, String newPassword) {
        account.setPassword(passwordEncoder.encode(newPassword));
        repository.save(account);
    }

    @Override
    public boolean checkIfValidOldPassword(Account account, String oldPassword) {
        return passwordEncoder.matches(oldPassword, account.getPassword());
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Account account = repository.findByEmail(username);
//        if (account == null){
//            throw new UsernameNotFoundException("No User Found");
//        }
//        return new org.springframework.security.core.userdetails.User(account.getUsername(),
//                account.getPassword(),
//                account.isEnabled(),
//                true,
//                true,
//                true,
//                getAuthorities(List.of(account.getRole())));
//    }
//
//    private Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (String role: roles){
//            authorities.add(new SimpleGrantedAuthority(role));
//        }
//        return authorities;
//    }
}
