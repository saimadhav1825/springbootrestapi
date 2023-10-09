package com.example.demo.repository;


import com.example.demo.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    @Query("SELECT otp FROM OTP otp WHERE otp.user.id = :userId")
    Optional<Otp> findByUserId(@Param("userId") UUID userId);
}
