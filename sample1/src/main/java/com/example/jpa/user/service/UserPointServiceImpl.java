package com.example.jpa.user.service;

import com.example.jpa.board.model.ServiceResult;
import com.example.jpa.user.entity.AppUser;
import com.example.jpa.user.entity.UserPoint;
import com.example.jpa.user.model.UserPointInput;
import com.example.jpa.user.model.UserPointType;
import com.example.jpa.user.repository.UserPointRepository;
import com.example.jpa.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserPointServiceImpl implements UserPointService {

    private final UserPointRepository userPointRepository;
    private final UserRepository userRepository;

    @Override
    public ServiceResult addPoint(String email, UserPointInput userPointInput) {
        Optional<AppUser> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        AppUser user = optionalUser.get();

        userPointRepository.save(UserPoint.builder()
            .user(user)
            .userPointType(userPointInput.getUserPointType())
            .point(userPointInput.getUserPointType().getValue())
            .build());
        return ServiceResult.success();
    }
}
