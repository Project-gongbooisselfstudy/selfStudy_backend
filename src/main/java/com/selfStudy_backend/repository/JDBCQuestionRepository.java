package com.selfStudy_backend.repository;

import com.selfStudy_backend.domain.Question;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class JDBCQuestionRepository implements QuestionRepository {

    private final JdbcTemplate jdbcTemplate;
    private int idx = 0;
    private List<Integer> randomList = new ArrayList<>();

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public JDBCQuestionRepository(DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);
        makeRandomList();
        setIdx(0);
    }

    @Override
    public void saveQuestion(Question question) {
        String sql = "INSERT INTO TESTDB.testQuestion(question_id, wrong, user_id , question, classification , answer) VALUES (?,?,?,?,?,?)";
        Object[] Params = {question.getQuestion_id(), question.getWrong(), question.getUser_id(), question.getContents(),question.getClassification(), question.getAnswer()};
        jdbcTemplate.update(sql,Params);
    }

    @Override
    public List<Question> findAll() {
        return jdbcTemplate.query("select * from TESTDB.testQuestion",questionRowMapper());
    }

    @Override
    public List<Question> findById(String user_id) {
        String sql = "select * from TESTDB.testQuestion where user_id = ?";
        List<Question> result = jdbcTemplate.query(sql,questionRowMapper(),user_id);
        return result;
    }

    @Override
    public Optional<Question> findByQuestion(int question_id){
        String sql = "select * from TESTDB.testQuestion where question_id = ?";
        List<Question> result = jdbcTemplate.query(sql,questionRowMapper(),question_id);
        return result.stream().findFirst();
    }


    @Override
    public List<Question> updateQuestion(int question_id , String variable, String updateContents) {

        if (variable.equals("question")) {
            String sql = "UPDATE TESTDB.testQuestion SET question = ? WHERE question_id = ? ";
            Object [] params = {updateContents, question_id};
            jdbcTemplate.update(sql,params);
            String sql2 = "select * from TESTDB.testQuestion where question_id = ?";
            List<Question> result = jdbcTemplate.query(sql2,questionRowMapper(),question_id);
            return result;
        }
        else if (variable.equals("answer")) {
            String sql = "UPDATE TESTDB.testQuestion SET answer = ? WHERE question_id = ? ";
            Object [] params = {updateContents, question_id};
            jdbcTemplate.update(sql,params);
            String sql2 = "select * from TESTDB.testQuestion where question_id = ?";
            List<Question> result = jdbcTemplate.query(sql2,questionRowMapper(),question_id);
            return result;
        }
        else if (variable.equals("classification")) {
            String sql = "UPDATE TESTDB.testQuestion SET classification = ? WHERE question_id = ? ";
            Object [] params = {updateContents, question_id};
            jdbcTemplate.update(sql,params);
            String sql2 = "select * from TESTDB.testQuestion where question_id = ?";
            List<Question> result = jdbcTemplate.query(sql2,questionRowMapper(),question_id);
            return result;
        }
        return null;
    }

    @Override
    public String deleteQuestion(int question_id){
        String sql = "delete from TESTDB.testQuestion where question_id = ?";
        jdbcTemplate.update(sql,question_id);
        return "해당 문제가 삭제되었습니다";
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
        String sql = "SELECT max(question_id) FROM TESTDB.testQuestion;";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count;
    }


    private RowMapper<Integer> questionIDRowMapper() {
        return (rs, rowNum) -> {
            Question question = new Question();
            question.setQuestion_id(rs.getInt("question_id"));
            return question.getQuestion_id();
        };
    }


    public void makeRandomList() {
        String sql = "select question_id from TESTDB.testQuestion";
        randomList = jdbcTemplate.query(sql,questionIDRowMapper());
        Collections.shuffle(randomList);
    }

    private RowMapper<Question> randomRowMapper() {
        return (rs, rowNum) -> {
            Question qu = new Question();
            qu.setQuestion_id(rs.getInt("question_id"));
            qu.setContents(rs.getString("question"));
            qu.setClassification(rs.getString("classification"));
            qu.setAnswer(rs.getString("answer"));
            return qu;
        };
    }

    //TODO 더이상의 문제가 없는데 idx를 계속 늘리는 경우도 대비해야함.
    @Override
    public List<Question> randomNext() {
        String sql = "select question, classification, answer from TESTDB.testQuestion where question_id= ? ";
        try {
            List<Question> result = jdbcTemplate.query(sql,randomRowMapper(),randomList.get(idx));
            idx+=1 ;
            return result;
        }
        catch (Exception e ) {
            System.out.println(e.getMessage());
            System.out.println("더이상의 문제가 없습니다");
            return  jdbcTemplate.query(sql,randomRowMapper(),randomList.get(idx-1)); }
    }

    @Override
    public List<Question> randomPrev() {
        String sql = "select  question, classification, answer from TESTDB.testQuestion where question_id = ?";
        try {
            List<Question> result = jdbcTemplate.query(sql,randomRowMapper(),randomList.get(idx));
            idx-=1 ;
            return result;
        }
        catch (Exception e ) {
            System.out.println("더이상의 문제가 없습니다");
            return  jdbcTemplate.query(sql,randomRowMapper(),randomList.get(idx+1)); }
    }


    public List<Question> updateWrong(int question_id) {
        String sql = "UPDATE TESTDB.testQuestion SET wrong = ? WHERE question_id = ? ";
        Object [] params = {1, question_id};
        jdbcTemplate.update(sql,params);
        String sql2 = "select * from TESTDB.testQuestion where question_id = ?";
        List<Question> result = jdbcTemplate.query(sql2,questionRowMapper(),question_id);
        return result;
    }

}
