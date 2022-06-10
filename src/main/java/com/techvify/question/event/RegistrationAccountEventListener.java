package com.techvify.question.event;

import com.techvify.question.entity.Account;
import com.techvify.question.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
 @Slf4j
public class RegistrationAccountEventListener implements ApplicationListener<RegistrationAccountEvent> {
    @Autowired
    private AccountService accountService;
    @Override
    public void onApplicationEvent(RegistrationAccountEvent event) {
        Account account = event.getAccount();
        String token = UUID.randomUUID().toString();
        accountService.saveVerificationTokenForAccount(token, account);

        String url = event.getApplicationUrl() + "/api/v1/verifyRegistration?token="+token;
        log.info("Click the link to verify your account: {}", url);
    }
}
