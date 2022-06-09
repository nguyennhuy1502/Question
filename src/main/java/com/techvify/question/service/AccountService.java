package com.techvify.question.service;

import com.techvify.question.DTO.RegistrationAccountDTO;
import com.techvify.question.entity.Account;
import com.techvify.question.entity.VerificationToken;
import com.techvify.question.repository.IAccountRepository;
import com.techvify.question.repository.IVerificationTokenRepository;
import com.techvify.question.service.isevice.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private IAccountRepository repository;

    @Autowired
    private IVerificationTokenRepository verificationTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
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
}
