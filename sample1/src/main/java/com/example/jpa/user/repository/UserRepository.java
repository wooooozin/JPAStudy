package com.example.jpa.user.repository;

import com.example.jpa.user.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    int countByEmail(String email);

    Optional<AppUser> findByIdAndPassword(Long id, String password);

    Optional<AppUser> findByUserNameAndPhone(String userName, String phone);

    Optional<AppUser> findByEmail(String email);
}
