package com.techvify.question.event;

import com.techvify.question.entity.Account;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationAccountEvent extends ApplicationEvent {

    private final Account account;
    private final String applicationUrl;
    public RegistrationAccountEvent(Account account, String applicationUrl) {
        super(account);
        this.account = account;
        this.applicationUrl = applicationUrl;
    }
}
