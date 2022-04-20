package com.selfStudy_backend.repository;

import com.selfStudy_backend.domain.Question;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

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
    }

    // id별 문제 전체 조회
    @Override
    public List<Question> findById(String user_id) {
        String sql = "select * from testQuestion where user_id = ?";
        List<Question> result = jdbcTemplate.query(sql,questionRowMapper(),user_id);
        return result;
    }

    // 문제 update (question/ answer / classification)
    @Override
    public List<Question> updateQuestion(int question_id , String variable, String updateContents) {

        if (variable.equals("question")) {
            String sql = "UPDATE testQuestion SET question = ? WHERE question_id = ? ";
            Object [] params = {updateContents, question_id};
            jdbcTemplate.update(sql,params);
            String sql2 = "select * from testQuestion where question_id = ?";
            List<Question> result = jdbcTemplate.query(sql2,questionRowMapper(),question_id);
            return result;
        }
        else if (variable.equals("answer")) {
            String sql = "UPDATE testQuestion SET answer = ? WHERE question_id = ? ";
            Object [] params = {updateContents, question_id};
            jdbcTemplate.update(sql,params);
            String sql2 = "select * from testQuestion where question_id = ?";
            List<Question> result = jdbcTemplate.query(sql2,questionRowMapper(),question_id);
            return result;
        }
        else if (variable.equals("classification")) {
            String sql = "UPDATE testQuestion SET classification = ? WHERE question_id = ? ";
            Object [] params = {updateContents, question_id};
            jdbcTemplate.update(sql,params);
            String sql2 = "select * from testQuestion where question_id = ?";
            List<Question> result = jdbcTemplate.query(sql2,questionRowMapper(),question_id);
            return result;
        }
        return null;
    }

    // 문제 삭제
    @Override
    public String deleteQuestion(int question_id){
        String sql = "delete from testQuestion where question_id = ?";
        jdbcTemplate.update(sql,question_id);
        return "해당 문제가 삭제되었습니다";
    }

    // TODO 인덱스값 이상으로 넘어갔을 때 어떻게 처리할지?
    public List<Question> loadQuestion() {
        String sql = "select question, user_id, classification, answer from TESTDB.testQuestion where question_id= ? ";
        System.out.println("Random List " + randomList);
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

    public List<Question> updateWrong(int question_id,String user_id) {
        String sql = "UPDATE TESTDB.testQuestion SET wrong = ? WHERE question_id = ? ";
        Object [] params = {1, question_id};
        jdbcTemplate.update(sql,params);
        String sql2 = "select * from TESTDB.testQuestion where question_id = ?";
        List<Question> result = jdbcTemplate.query(sql2,questionRowMapper(),question_id);
        String sql3 = "INSERT INTO testWrong(wrong_id,question_id,user_id) VALUES (?,?,?)";
        Object[] Params = {getWrong_id() ,question_id, user_id};
        jdbcTemplate.update(sql3,Params);
        return result;
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


    private void makeRandomList() {
        String sql = "select question_id from testQuestion";
        randomList = jdbcTemplate.query(sql,questionIDMapper());
        Collections.shuffle(randomList);
    }

    //controller에서 사용하는 메소드
    public int getQuestion_id(){
        try {
        String sql = "SELECT max(question_id) FROM testQuestion;";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count; }
        catch (Exception e) { int count = 1 ; return count;}
    }

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
