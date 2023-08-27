package com.example.jpa.user.repository;

import com.example.jpa.user.model.UserLogCount;
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

    public List<UserLogCount> findUserLogCount() {
        String sql = "SELECT u.id, u.email, u.user_name, " +
                "(SELECT COUNT(*) FROM notice n WHERE n.user_id = u.id) AS notice_count, " +
                "(SELECT SUM(n.likes) FROM notice n WHERE n.user_id = u.id) AS notice_like_count " +
                "FROM App_User u";

        List<UserLogCount> list = entityManager.createNativeQuery(sql).getResultList();
        return list;
    }

    public List<UserLogCount> getBestLikeUser() {
        String sql = "SELECT u.*, " +
                "(SELECT COUNT(*) FROM notice_like nl WHERE nl.user_id = u.id) AS notice_like_count " +
                "FROM App_User u ORDER BY notice_like_count DESC";


        List<UserLogCount> list = entityManager.createNativeQuery(sql).getResultList();
        return list;
    }
}
