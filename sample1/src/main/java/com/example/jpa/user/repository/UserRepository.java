package com.example.jpa.user.repository;

import com.example.jpa.user.entity.AppUser;
import com.example.jpa.user.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    int countByEmail(String email);

    Optional<AppUser> findByIdAndPassword(Long id, String password);

    Optional<AppUser> findByUserNameAndPhone(String userName, String phone);

    Optional<AppUser> findByEmail(String email);

    List<AppUser> findByEmailContainsOrUserNameContainsOrPhoneNotContains(
            String email, String userName, String phone
    );

    long countByStatus(UserStatus userStatus);

    @Query("select u from AppUser u where u.regDate between :startDate and :endDate")
    List<AppUser> findToday(LocalDateTime startDate, LocalDateTime endDate);
}
