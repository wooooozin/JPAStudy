package com.example.jpa.user.service;

import com.example.jpa.board.model.ServiceResult;
import com.example.jpa.common.exception.BizException;
import com.example.jpa.common.model.ResponseResult;
import com.example.jpa.logs.service.LogsService;
import com.example.jpa.user.entity.AppUser;
import com.example.jpa.user.entity.UserInterest;
import com.example.jpa.user.model.UserLogCount;
import com.example.jpa.user.model.UserLogin;
import com.example.jpa.user.model.UserNoticeCount;
import com.example.jpa.user.model.UserStatus;
import com.example.jpa.user.model.UserSummary;
import com.example.jpa.user.repository.UserCustomRepository;
import com.example.jpa.user.repository.UserInterestRepository;
import com.example.jpa.user.repository.UserRepository;
import com.example.jpa.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserCustomRepository userCustomRepository;
    private final UserInterestRepository userInterestRepository;
    private final LogsService logsService;

    @Override
    public UserSummary getUserStatusCount() {
        long usingUserCount = userRepository.countByStatus(UserStatus.Using);
        long stopUserCount = userRepository.countByStatus(UserStatus.Stop);
        long totalUserCount = userRepository.count();
        return UserSummary.builder()
                .usingUserCount(usingUserCount)
                .stopUserCount(stopUserCount)
                .totalUserCount(totalUserCount)
                .build();
    }

    @Override
    public List<AppUser> getTodayUsers() {
        LocalDateTime t = LocalDateTime.now();
        LocalDateTime startDate = LocalDateTime.of(
                t.getYear(), t.getMonth(), t.getDayOfMonth(), 0, 0
        );
        LocalDateTime endDate = startDate.plusDays(1);

        return  userRepository.findToday(startDate, endDate);
    }

    @Override
    public List<UserNoticeCount> getUserNoticeCount() {

        return userCustomRepository.findUserNoticeCount();
    }

    @Override
    public List<UserLogCount> getUserLogCount() {
        return userCustomRepository.findUserLogCount();
    }

    @Override
    public List<UserLogCount> getBestLikeUser() {
        return userCustomRepository.getBestLikeUser();
    }

    @Override
    public ServiceResult addInterestUser(Long id, String email) {
        Optional<AppUser> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        AppUser user = optionalUser.get();

        Optional<AppUser> optionalInterestUser = userRepository.findById(id);
        if (!optionalInterestUser.isPresent()) {
            return ServiceResult.fail("관심 사용자에 추가할 유저가 없습니다.");
        }
        AppUser interestUser = optionalInterestUser.get();

        if (user.getId() == interestUser.getId()) {
            return ServiceResult.fail("자기 자신을 추가할 수 없습니다.");
        }

        if (userInterestRepository.countByUserAndInterestUser(user, interestUser) > 0) {
            return ServiceResult.fail("이미 관심사용자 목록에 추가되어 있습니다.");
        }
        UserInterest userInterest = UserInterest.builder()
                .user(user)
                .interestUser(interestUser)
                .regDate(LocalDateTime.now())
                .build();
        userInterestRepository.save(userInterest);

        return ServiceResult.success();
    }

    @Override
    public ServiceResult removeInterestUser(Long id, String email) {
        Optional<AppUser> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        AppUser user = optionalUser.get();

        Optional<UserInterest> optionalInterestUser = userInterestRepository.findById(id);
        if (!optionalInterestUser.isPresent()) {
            return ServiceResult.fail("삭제할 정보가 없습니다.");
        }
        UserInterest interestUser = optionalInterestUser.get();

        if (interestUser.getUser().getId() != user.getId()) {
            return ServiceResult.fail("본인의 관심자 정보만 삭제할 수 있습니다.");
        }

        userInterestRepository.delete(interestUser);
        return ServiceResult.success();
    }

    @Override
    public AppUser login(UserLogin userLogin) {
        Optional<AppUser> optionalUser = userRepository.findByEmail(userLogin.getEmail());
        if (!optionalUser.isPresent()) {
            throw new BizException("회원 정보가 없습니다.");
        }
        AppUser user = optionalUser.get();
        if (!PasswordUtils.equalPassword(userLogin.getPassword(), user.getPassword())) {
            throw new BizException("일치하지 정보가 없습니다.");
        }

        return user;
    }
}
