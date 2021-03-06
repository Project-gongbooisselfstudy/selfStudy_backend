package com.selfStudy_backend.repository;

import com.selfStudy_backend.domain.CookieUser;
import com.selfStudy_backend.domain.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Slf4j
@Repository
public class JDBCQuestionRepository implements QuestionRepository {

    private  JdbcTemplate jdbcTemplate;
    private int idx = 0;
    private int randomIdx;
    private List<Integer> randomList;
    private JDBCWrongRepository jdbcWrongRepository;

    @Autowired
    public JDBCQuestionRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcWrongRepository = new JDBCWrongRepository(dataSource);
    }


    // 문제 DB에 저장
    @Override
    public void saveQuestion(Question question) {
        String sql = "INSERT INTO testQuestion(question_id, wrong, user_id , question, classification , answer) VALUES (?,?,?,?,?,?)";
        Object[] Params = {question.getQuestion_id(), question.getWrong(), question.getUser_id(), question.getContents(),question.getClassification(), question.getAnswer()};
        jdbcTemplate.update(sql,Params);
        log.debug("testQuestion에 문제를 저장");
    }

    // id별 문제 전체 조회
    @Override
    public List<Question> findById(String user_id) {
        String sql = "select * from testQuestion where user_id = ?";
        List<Question> result = jdbcTemplate.query(sql,questionRowMapper(),user_id);
        log.info("userID에 해당하는 문제 전체 조회");
        return result;
    }

    // 문제 update (question/ answer / classification)
    @Override
    public List<Question> updateQuestion(int question_id , String category, String updateContents) {

        switch (category) {
            case "question": {
                log.info("category : question");
                String sql = "UPDATE testQuestion SET question = ? WHERE question_id = ? ";
                Object[] params = {updateContents, question_id};
                jdbcTemplate.update(sql, params);
                log.info("questionID에 해당하는 question 수정");
                String sql2 = "select * from testQuestion where question_id = ?";
                List<Question> result = jdbcTemplate.query(sql2, questionRowMapper(), question_id);
                return result;
            }
            case "answer": {
                log.info("category : answer");
                String sql = "UPDATE testQuestion SET answer = ? WHERE question_id = ? ";
                Object[] params = {updateContents, question_id};
                jdbcTemplate.update(sql, params);
                log.info("questionID에 해당하는 answer 수정");
                String sql2 = "select * from testQuestion where question_id = ?";
                List<Question> result = jdbcTemplate.query(sql2, questionRowMapper(), question_id);
                return result;
            }
            case "classification": {
                log.info("category : classification");
                String sql = "UPDATE testQuestion SET classification = ? WHERE question_id = ? ";
                Object[] params = {updateContents, question_id};
                jdbcTemplate.update(sql, params);
                log.info("questionID에 해당하는 classification 수정");
                String sql2 = "select * from testQuestion where question_id = ?";
                List<Question> result = jdbcTemplate.query(sql2, questionRowMapper(), question_id);
                return result;
            }
        }
        return null;
    }

    // 문제 삭제
    @Override
    public String deleteQuestion(int question_id){
        String sql = "delete from testQuestion where question_id = ?";
        jdbcTemplate.update(sql,question_id);
        log.info("다시 랜덤으로 문제를 생성");
        makeRandomList();
        log.info("해당 문제가 삭제되었습니다.");
        return "해당 문제가 삭제되었습니다";
    }


    public List<Question> loadNext() {
        String sql =  "select question, user_id, classification, answer from testQuestion where question_id= ? ";
        log.info("Question Random List : " + randomList);
        try {
            randomIdx = randomList.get(idx++);
            List<Question> result = jdbcTemplate.query(sql,randomMapper(),randomIdx);
            return result;
        }
        catch (Exception e ) {
            log.info("Question loadNext : 더이상의 문제가 없습니다");
            randomIdx = randomList.get(randomList.size()-1);
            List<Question> result = jdbcTemplate.query(sql,randomMapper(),randomIdx);
            return result;}
    }

    public List<Question> loadPrev() {
        String sql = "select question, user_id, classification, answer from testQuestion where question_id= ? ";
        try {
            randomIdx = randomList.get(--idx);
            List<Question> result = jdbcTemplate.query(sql,randomMapper(),randomIdx);
            return result;
        }
        catch (Exception e) {
            log.info("Question loadPrev : 더이상의 문제가 없습니다");
            randomIdx = randomList.get(0);
            List<Question> result = jdbcTemplate.query(sql,randomMapper(),randomIdx);
            return result;
        }
    }




    // 오답인 경우, testQuestionDB의 wrong값을 변경해야함
    public List<Question> updateWrong(int question_id,String user_id) {
        String sql = "UPDATE testQuestion SET wrong = ? WHERE question_id = ? ";
        Object [] params = {1, question_id};
        jdbcTemplate.update(sql,params);
        log.info("wrong의 값을 1로 업데이트");
        String sql2 = "select * from testQuestion where question_id = ?";
        List<Question> result = jdbcTemplate.query(sql2,questionRowMapper(),question_id);
        try {
        String sql3 = "INSERT INTO testWrong(wrong_id,question_id,user_id) VALUES (?,?,?)";
        int count = controller_getWrong_id();
        Object[] Params = {count ,question_id, user_id};
        jdbcTemplate.update(sql3,Params);
        log.info("testWrong 테이블에 insert"); }
        catch (Exception e){
            log.error(e.getMessage());
        }
        jdbcWrongRepository.makeRandomList();
        return result;
    }

    // 랜덤으로 문제 출제
    public void makeRandomList() {
        String user_id = CookieUser.getG_id();
        log.info("user_id = " + user_id);
        String sql = "select question_id from testQuestion where user_id=?";
        randomList = jdbcTemplate.query(sql,questionIDMapper(),user_id);
        Collections.shuffle(randomList);
    }


    private RowMapper<Question> questionRowMapper() {
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


    //controller에서 사용하는 메소드
    public int controller_getQuestion_id(){
        try {
        String sql = "SELECT max(question_id) FROM testQuestion;";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count; }
        catch (Exception e) { int count = 1 ; return count;}
    }

    private int controller_getWrong_id() {
        try{
        String sql = "SELECT max(wrong_id) FROM testWrong;";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        return ++count; }
        catch (NullPointerException e) { int count = 1 ; return count;}
    }

    private RowMapper<Integer> questionIDMapper() {
        return (rs, rowNum) -> {
            Question question = new Question();
            question.setQuestion_id(rs.getInt("question_id"));
            return question.getQuestion_id();
        };
    }

    private RowMapper<Question> randomMapper() {
        return (rs, rowNum) -> {
            Question qu = new Question();
            qu.setQuestion_id(randomIdx);
            qu.setUser_id(rs.getString("user_id"));
            qu.setContents(rs.getString("question"));
            qu.setClassification(rs.getString("classification"));
            qu.setAnswer(rs.getString("answer"));
            return qu;
        };
    }


}
