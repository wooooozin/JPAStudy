package com.example.jpa.user.repository;

import com.example.jpa.user.model.UserNoticeCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class UserCustomRepository {
    private final EntityManager entityManager;

    public List<UserNoticeCount> findUserNoticeCount() {
        String sql = "select u.id, u.email, u.user_name, (select count(*) from Notice n where n.user_id = u.id) notice_count from App_User u";
        List<UserNoticeCount> list = entityManager.createNativeQuery(sql).getResultList();
        return list;
    }
}