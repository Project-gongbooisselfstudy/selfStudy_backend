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
        makeRandomList();
    }


    // id별 오답문제 전체 조회
    public List<Question> findAll(String user_id) {
        String sql = "select * from testQuestion where user_id = ? and wrong = 1";
        List<Question> result = jdbcTemplate.query(sql, questionMapper(), user_id);
        return result;
    }

    // TODO id 별로 나눠서 조회할 수 있도록 변경해야함
    public List<Question> loadWrong() {
        String sql = "select q.question , q.answer , q.classification from testQuestion as q join testWrong as w on q.question_id = w.question_id where w.wrong_id=? ";
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



    public void updateWrong(int question_id) {
        String sql = "UPDATE testQuestion SET wrong = ? WHERE question_id = ? ";
        Object [] params = {0, question_id}; // 정답이므로 0으로 다시 변경
        jdbcTemplate.update(sql,params);
        String sql2 = "select * from testQuestion where question_id = ?";
        List<Question> result = jdbcTemplate.query(sql2,questionMapper(),question_id);
        String sql3 = "delete from testWrong where wrong_id = ?"; // wrong 테이블에서 값 제거
        jdbcTemplate.update(sql3,question_id);
        System.out.println("정답이므로 wrongDB에서 문제 삭제");
    }


    public void makeRandomList() {
        String sql = "select wrong_id from testWrong";
        randomList = jdbcTemplate.query(sql, wrongIdMapper());
        Collections.shuffle(randomList);
    }

    private RowMapper<Integer> wrongIdMapper() {
        return (rs, rowNum) -> {
            Wrong wrong = new Wrong();
            wrong.setWrong_id(rs.getInt("wrong_id"));
            return wrong.getWrong_id();
        };
    }


    private RowMapper<Question> questionMapper() {
        return (rs, rowNum) -> {
            Question question = new Question();
            question.setQuestion_id(rs.getInt("question_id"));
            question.setWrong(rs.getInt("wrong"));
            question.setUser_id(rs.getString("user_id"));
            question.setContents(rs.getString("question"));
            question.setClassification(rs.getString("classification"));
            question.setAnswer(rs.getString("answer"));
            return question;
        };
    }

    private RowMapper<Question> randomMapper() {
        return (rs, rowNum) -> {
            Question qu = new Question();
            qu.setQuestion_id(randomList.get(idx));
//            qu.setUser_id(rs.getString("user_id"));
            qu.setContents(rs.getString("question"));
            qu.setClassification(rs.getString("classification"));
            qu.setAnswer(rs.getString("answer"));
            return qu;
        };
    }
}
