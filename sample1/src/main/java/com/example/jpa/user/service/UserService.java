package com.example.jpa.user.service;

import com.example.jpa.board.model.ServiceResult;
import com.example.jpa.user.entity.AppUser;
import com.example.jpa.user.model.UserLogCount;
import com.example.jpa.user.model.UserLogin;
import com.example.jpa.user.model.UserNoticeCount;
import com.example.jpa.user.model.UserSummary;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    UserSummary getUserStatusCount();

    List<AppUser> getTodayUsers();

    List<UserNoticeCount> getUserNoticeCount();

    List<UserLogCount> getUserLogCount();

    List<UserLogCount> getBestLikeUser();

    ServiceResult addInterestUser(Long id, String email);

    ServiceResult removeInterestUser(Long id, String email);

    AppUser login(UserLogin userLogin);

}
