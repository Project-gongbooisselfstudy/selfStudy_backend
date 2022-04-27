package com.selfStudy_backend.repository;

import com.selfStudy_backend.domain.CookieUser;
import com.selfStudy_backend.domain.Question;
import com.selfStudy_backend.domain.Wrong;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

@Slf4j
@Repository
public class JDBCWrongRepository implements WrongRepository {

    private final JdbcTemplate jdbcTemplate;
    private int idx = 0;
    private int randomIdx;
    private List<Integer> randomList;

    public JDBCWrongRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
//        makeRandomList();
    }


    // id별 오답문제 전체 조회
    public List<Question> findAll(String user_id) {
        String sql = "select * from testQuestion where user_id = ? and wrong = 1";
        List<Question> result = jdbcTemplate.query(sql, questionMapper(), user_id);
        log.info( user_id + "의 오답문제 전체 조회");
        return result;
    }


    public List<Question> loadNext() {
        String sql =  "select q.question , q.answer , q.classification from testQuestion as q join testWrong as w on q.question_id = w.question_id where w.wrong_id=? ";
        log.info("Wrong Random List " + randomList);
        try {
            randomIdx = randomList.get(idx++);
            List<Question> result = jdbcTemplate.query(sql,randomMapper(),randomIdx);
            return result;
        }
        catch (Exception e ) {
            log.info("Wrong loadNext : 더이상의 문제가 없습니다");
            randomIdx = randomList.get(randomList.size()-1);
            List<Question> result = jdbcTemplate.query(sql,randomMapper(),randomIdx);
            return result;}
    }

    public List<Question> loadPrev() {
        String sql = "select q.question , q.answer , q.classification from testQuestion as q join testWrong as w on q.question_id = w.question_id where w.wrong_id=? ";
        log.info("Wrong Random List " + randomList);
        try {
            randomIdx = randomList.get(--idx);
            List<Question> result = jdbcTemplate.query(sql,randomMapper(),randomIdx);
            return result;
        }
        catch (Exception e ) {
            log.info("Wrong loadPrev : 더이상의 문제가 없습니다");
            randomIdx = randomList.get(0);
            List<Question> result = jdbcTemplate.query(sql,randomMapper(),randomIdx);
            return result;
        }
    }



    public void updateWrong(int question_id) {
        String sql = "UPDATE testQuestion SET wrong = ? WHERE question_id = ? ";
        Object [] params = {0, question_id}; // 정답이므로 0으로 다시 변경
        jdbcTemplate.update(sql,params);
        log.info("wrong의 값을 1로 업데이트");
        String sql2 = "select * from testQuestion where question_id = ?";
        List<Question> result = jdbcTemplate.query(sql2,questionMapper(),question_id);
        String sql3 = "delete from testWrong where wrong_id = ?"; // wrong 테이블에서 값 제거
        jdbcTemplate.update(sql3,question_id);
        log.info("정답 :: testWrongDB에서 삭제");
    }


    public void makeRandomList() {
        String user_id = CookieUser.getG_id();
        log.info("Wrong user_id = " + user_id);
        String sql = "select wrong_id from testWrong where user_id=?";
        randomList = jdbcTemplate.query(sql,wrongIdMapper(),user_id);
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
            qu.setQuestion_id(randomIdx);
            qu.setContents(rs.getString("question"));
            qu.setClassification(rs.getString("classification"));
            qu.setAnswer(rs.getString("answer"));
            return qu;
        };
    }
}
