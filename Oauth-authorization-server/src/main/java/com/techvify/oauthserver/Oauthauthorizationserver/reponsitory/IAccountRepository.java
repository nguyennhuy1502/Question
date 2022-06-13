package com.techvify.oauthserver.Oauthauthorizationserver.reponsitory;


import com.techvify.oauthserver.Oauthauthorizationserver.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Integer> {

    Account findByEmail(String email);
}
