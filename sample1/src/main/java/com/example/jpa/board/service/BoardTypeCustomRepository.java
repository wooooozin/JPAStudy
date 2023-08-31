package com.example.jpa.board.service;

import com.example.jpa.board.model.BoardTypeCount;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class BoardTypeCustomRepository {
    private final EntityManager entityManager;

    public List<BoardTypeCount> getBoardTypeCount() {
        String sql = "SELECT \n" +
                "    bt.id, \n" +
                "    bt.board_name, \n" +
                "    bt.reg_date, \n" +
                "    bt.using_yn,\n" +
                "    (SELECT count(*) FROM board b WHERE b.board_type_id = bt.id) AS board_count \n" +
                "FROM \n" +
                "    board_type bt;\n";

//        List<Object[]> result = entityManager.createNativeQuery(sql).getResultList();
//        List<BoardTypeCount> list = result.stream().map(e -> new BoardTypeCount(e))
//                .collect(Collectors.toList());

        Query naviteQuery = entityManager.createNativeQuery(sql);
        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<BoardTypeCount> list = jpaResultMapper.list(naviteQuery, BoardTypeCount.class);

        return list;
    }


}
