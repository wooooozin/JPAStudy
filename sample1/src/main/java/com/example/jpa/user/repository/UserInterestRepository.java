package com.example.jpa.user.repository;

import com.example.jpa.user.entity.AppUser;
import com.example.jpa.user.entity.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInterestRepository extends JpaRepository<UserInterest, Long> {
    long countByUserAndInterestUser(AppUser user, AppUser interestUser);
}
