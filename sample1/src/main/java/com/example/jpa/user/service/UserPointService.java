package com.example.jpa.user.service;

import com.example.jpa.board.model.ServiceResult;
import com.example.jpa.user.entity.AppUser;
import com.example.jpa.user.model.UserLogCount;
import com.example.jpa.user.model.UserNoticeCount;
import com.example.jpa.user.model.UserPointInput;
import com.example.jpa.user.model.UserPointType;
import com.example.jpa.user.model.UserSummary;
import java.util.List;


public interface UserPointService {
    ServiceResult addPoint(String email, UserPointInput userPointInput);
}
