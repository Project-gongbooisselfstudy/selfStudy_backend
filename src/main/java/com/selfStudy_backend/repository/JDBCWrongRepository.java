package com.selfStudy_backend.repository;

import com.selfStudy_backend.domain.Question;
import com.selfStudy_backend.domain.Wrong;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class JDBCWrongRepository implements WrongRepository {

    private List store = new ArrayList<>();
    private final JdbcTemplate jdbcTemplate;
    private int idx = 0;
    private List<Integer> randomList;


    public JDBCWrongRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    // id별 문제 전체 조회
    public List<Wrong> findAll(String user_id) {
        String sql = "select * from testWrong where user_id = ?";
        System.out.println("wrongrepository 실행");
        List<Wrong> result = jdbcTemplate.query(sql, wrongRowMapper(), user_id);
        return result;
    }




    // TODO id 별로 나눠서 조회할 수 있도록 변경해야함
    public List<Question> loadWrong() {
        //다시
//        String sql = "select question, user_id, classification, answer from TESTDB.testQuestion where question_id= ? ";
        String sql = "select q.question , q.answer , q.classification from testQuestion as q join testWrong as w on q.question_id = w.question_id where w.wrong_id=?";
        System.out.println("Random List " + randomList);
        if (idx < randomList.size()) {
            List<Question> result = jdbcTemplate.query(sql, randomMapper(), randomList.get(idx));
            idx += 1;
            return result;
        } else {
            idx -= 1;
            List<Question> result = jdbcTemplate.query(sql, randomMapper(), randomList.get(idx));
            return result;
        }
    }


    public List<Wrong> updateWrong() {
        return null;
    }


    private RowMapper<Wrong> wrongRowMapper() {
        return (rs, rowNum) -> {
            Wrong wrong = new Wrong();
            wrong.setWrong_id(rs.getInt("wrong_id"));
            wrong.setQuestion_id(rs.getInt("question_id"));
            wrong.setUser_id(rs.getString("user_id"));
            return wrong;
        };
    }

    public void makeRandomList() {
        String sql = "select wrong_id from testWrong";
        randomList = jdbcTemplate.query(sql, wrongIdMapper());
        Collections.shuffle(randomList);
    }

    private RowMapper<Integer> wrongIdMapper() {
        return (rs, rowNum) -> {
            Wrong wrong = new Wrong();
            wrong.setQuestion_id(rs.getInt("wrong_id"));
            return wrong.getWrong_id();
        };
    }

    private RowMapper<Question> randomMapper() {
        return (rs, rowNum) -> {
            Question qu = new Question();
            qu.setQuestion_id(randomList.get(idx));
            qu.setUser_id(rs.getString("user_id"));
            qu.setContents(rs.getString("question"));
            qu.setClassification(rs.getString("classification"));
            qu.setAnswer(rs.getString("answer"));
            return qu;
        };
    }
}
