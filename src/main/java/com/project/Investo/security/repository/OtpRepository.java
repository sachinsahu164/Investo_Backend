package com.project.Investo.security.repository;


import com.project.Investo.security.entity.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpVerification, Long> {

    Optional<OtpVerification> findByMobileNumber(String mobile);
}