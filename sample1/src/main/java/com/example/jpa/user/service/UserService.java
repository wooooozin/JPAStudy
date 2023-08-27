package com.example.jpa.user.service;

import com.example.jpa.user.entity.AppUser;
import com.example.jpa.user.model.UserSummary;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    UserSummary getUserStatusCount();

    List<AppUser> getTodayUsers();
}
