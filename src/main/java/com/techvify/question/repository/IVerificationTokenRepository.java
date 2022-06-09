package com.techvify.question.repository;

import com.techvify.question.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {
}
