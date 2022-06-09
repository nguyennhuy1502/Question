package com.techvify.question.controller;

import com.techvify.question.DTO.RegistrationAccountDTO;
import com.techvify.question.entity.Account;
import com.techvify.question.event.RegistrationAccountEvent;
import com.techvify.question.service.isevice.IAccountService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class AccountController {
    @Autowired
    private IAccountService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping("/hello")
    public List<Account> finAll() {
        return service.finAll();
    }

    @PostMapping("/register")
    public String registerAccount(@RequestBody RegistrationAccountDTO registrationAccountDTO, final HttpServletRequest request) {
        Account account = service.registerAccount(registrationAccountDTO);
        publisher.publishEvent(new RegistrationAccountEvent(
                account, applicationUrl(request)
        ));
        return "Success";
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
