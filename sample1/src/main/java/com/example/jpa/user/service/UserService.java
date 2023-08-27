package com.example.jpa.user.service;

import com.example.jpa.user.model.UserSummary;
import org.springframework.stereotype.Service;


public interface UserService {
    UserSummary getUserStatusCount();
}
