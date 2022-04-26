package com.selfStudy_backend.repository;

import com.selfStudy_backend.domain.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Slf4j
@Repository
public class JDBCQuestionRepository implements QuestionRepository {

    private final JdbcTemplate jdbcTemplate;
    private int idx = 0;
    private List<Integer> randomList;


    public JDBCQuestionRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        makeRandomList();  // 자꾸 2번씩 호출됨
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
        log.debug("userID에 해당하는 문제 전체 조회");
        return result;
    }

    // 문제 update (question/ answer / classification)
    @Override
    public List<Question> updateQuestion(int question_id , String category, String updateContents) {

        switch (category) {
            case "question": {
                log.debug("category : question");
                String sql = "UPDATE testQuestion SET question = ? WHERE question_id = ? ";
                Object[] params = {updateContents, question_id};
                jdbcTemplate.update(sql, params);
                log.debug("questionID에 해당하는 question 수정");
                String sql2 = "select * from testQuestion where question_id = ?";
                List<Question> result = jdbcTemplate.query(sql2, questionRowMapper(), question_id);
                return result;
            }
            case "answer": {
                log.debug("category : answer");
                String sql = "UPDATE testQuestion SET answer = ? WHERE question_id = ? ";
                Object[] params = {updateContents, question_id};
                jdbcTemplate.update(sql, params);
                log.debug("questionID에 해당하는 answer 수정");
                String sql2 = "select * from testQuestion where question_id = ?";
                List<Question> result = jdbcTemplate.query(sql2, questionRowMapper(), question_id);
                return result;
            }
            case "classification": {
                log.debug("category : classification");
                String sql = "UPDATE testQuestion SET classification = ? WHERE question_id = ? ";
                Object[] params = {updateContents, question_id};
                jdbcTemplate.update(sql, params);
                log.debug("questionID에 해당하는 classification 수정");
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
        log.debug("다시 랜덤으로 문제를 생성");
        makeRandomList();
        log.info("해당 문제가 삭제되었습니다.");
        return "해당 문제가 삭제되었습니다";
    }

    // TODO 인덱스값 이상으로 넘어갔을 때 어떻게 처리할지?
    public List<Question> loadQuestion() {
        String sql = "select question, user_id, classification, answer from TESTDB.testQuestion where question_id= ? ";
        log.debug("Random List " + randomList);
        if (idx < randomList.size()) {
            List<Question> result = jdbcTemplate.query(sql,randomMapper(),randomList.get(idx));
            idx+=1;
            return result;
        }
        else {
            idx-=1;
            List<Question> result = jdbcTemplate.query(sql,randomMapper(),randomList.get(idx));
            return result;}
    }

    // 오답인 경우, testQuestionDB의 wrong값을 변경해야함
    public List<Question> updateWrong(int question_id,String user_id) {
        String sql = "UPDATE testQuestion SET wrong = ? WHERE question_id = ? ";
        Object [] params = {1, question_id};
        jdbcTemplate.update(sql,params);
        log.debug("wrong의 값을 1로 업데이트");
        String sql2 = "select * from testQuestion where question_id = ?";
        List<Question> result = jdbcTemplate.query(sql2,questionRowMapper(),question_id);
        String sql3 = "INSERT INTO testWrong(wrong_id,question_id,user_id) VALUES (?,?,?)";
        int count = getWrong_id();
        Object[] Params = {++count ,question_id, user_id};
        jdbcTemplate.update(sql3,Params);
        log.debug("testWrong 테이블에 insert");
        return result;
    }

    // 랜덤으로 문제 출제
    public void makeRandomList() {
        String sql = "select question_id from testQuestion";
        randomList = jdbcTemplate.query(sql,questionIDMapper());
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
    public int getQuestion_id(){
        try {
        String sql = "SELECT max(question_id) FROM testQuestion;";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count; }
        catch (Exception e) { int count = 1 ; return count;}
    }

    // TODO 시작값이 이상함 고쳐야해 ..
    private int getWrong_id() {
        try{ String sql = "SELECT max(wrong_id) FROM testWrong;";
            int count = jdbcTemplate.queryForObject(sql, Integer.class);
            return count;}
        catch (Exception e) { int count = 1 ; return count;}
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
            qu.setQuestion_id(randomList.get(idx));
            qu.setUser_id(rs.getString("user_id"));
            qu.setContents(rs.getString("question"));
            qu.setClassification(rs.getString("classification"));
            qu.setAnswer(rs.getString("answer"));
            return qu;
        };
    }



}
