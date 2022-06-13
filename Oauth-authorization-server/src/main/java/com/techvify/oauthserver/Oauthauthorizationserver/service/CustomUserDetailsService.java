package com.techvify.oauthserver.Oauthauthorizationserver.service;

import com.techvify.oauthserver.Oauthauthorizationserver.entity.Account;
import com.techvify.oauthserver.Oauthauthorizationserver.reponsitory.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IAccountRepository accountRepository;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username);
        if (account == null){
            throw new UsernameNotFoundException("No User Found");
        }
        return new User(
                account.getUsername(),
                account.getPassword(),
                account.isEnabled(),
                true,
                true,
                true,
                getAuthorities(List.of(account.getRole()))

        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles){
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
