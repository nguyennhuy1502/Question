package com.techvify.question.controller;

import com.techvify.question.DTO.RegistrationAccountDTO;
import com.techvify.question.DTO.ResetPassword;
import com.techvify.question.entity.Account;
import com.techvify.question.entity.VerificationToken;
import com.techvify.question.event.RegistrationAccountEvent;
import com.techvify.question.service.isevice.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class AccountController {
    @Autowired
    private IAccountService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping("/all")
    public List<Account> finAll() {
        return service.finAll();
    }
    /**/
    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = service.validateVerificationToken(token);
        if (result.equalsIgnoreCase("valid")) {
            return "User verify successfully";
        }
        return "Bad User";
    }

    @GetMapping("/resendVerifyToken")
    public String resendVerifyToken(@RequestParam("token") String oldToken, HttpServletRequest request){
        VerificationToken verificationToken = service.generateNewVerificationToken(oldToken);
        Account account = verificationToken.getAccount();
        resendVerifyTokenMail(account, applicationUrl(request), verificationToken);
        return "Verification Link Sent";
    }


    @PostMapping("/register")
    public String registerAccount(@RequestBody RegistrationAccountDTO registrationAccountDTO, HttpServletRequest request) {
        Account account = service.registerAccount(registrationAccountDTO);
        publisher.publishEvent(new RegistrationAccountEvent(
                account, applicationUrl(request)
        ));
        return "success";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody ResetPassword resetPassword, HttpServletRequest request){
        Account account = service.findUserByEmail(resetPassword.getEmail());
        String url = "";
        if (account != null){
            String token = UUID.randomUUID().toString();
            service.createPasswordResetTokenForAccount(account,token);
            url = passwordResetTokenMail(account,applicationUrl(request), token);
        }
        return url;
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token, @RequestBody ResetPassword resetPassword){
        String result = service.validatePasswordResetToken(token);
        if (!result.equalsIgnoreCase("valid")){
            return "Invalid Token";
        }
        Optional<Account>account = service.getUserByPasswordResetToken(token);
        if (account.isPresent()){
            service.changePassword(account.get(), resetPassword.getNewPassword());
            return "Password Reset Successfully";
        }else {
            return "Invalid Token";
        }
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody ResetPassword resetPassword){
        Account account = service.findUserByEmail(resetPassword.getEmail());
        if (!service.checkIfValidOldPassword(account,resetPassword.getOldPassword())){
            return "Invalid Old Password";
        }

        service.changePassword(account,resetPassword.getNewPassword());
        return "Password Changed Successfully";
    }




    private void resendVerifyTokenMail(Account account, String applicationUrl, VerificationToken verificationToken) {
        String url = applicationUrl + "/api/v1/verifyRegistration?token="+verificationToken.getToken();
        log.info("Click the link to verify your account: {}", url);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    private String passwordResetTokenMail(Account account, String applicationUrl, String token) {
        String url = applicationUrl + "/api/v1/savePassword?token="+token;
        log.info("Click the link to reset your Password: {}", url);
        return url;
    }
}
