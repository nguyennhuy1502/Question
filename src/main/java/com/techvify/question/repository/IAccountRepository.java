package com.techvify.question.repository;

import com.techvify.question.entity.Account;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Integer> {

    Account findByEmail(String email);
}
