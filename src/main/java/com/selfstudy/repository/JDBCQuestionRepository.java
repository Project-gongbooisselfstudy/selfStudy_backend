package com.selfstudy.repository;

import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.result.Row;
import com.selfstudy.domain.Question;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JDBCQuestionRepository implements QuestionRepository {

    private List store = new ArrayList<>();
    private final JdbcTemplate jdbcTemplate;



    public JDBCQuestionRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveQuestion(Question question) {
        String sql = "INSERT INTO testQuestion(question_id, wrong, user_id , question, classification , answer) VALUES (?,?,?,?,?,?)";
        Object[] Params = {question.getQuestion_id(), question.getWrong(), question.getUser_id(), question.getContents(),question.getClassification(), question.getAnswer()};
        jdbcTemplate.update(sql,Params);

        System.out.println("========Repository : 저장 완료========");
    }

    @Override
    public List<Question> findAll() {
        System.out.println("==============Repository : findAll 실행==============");
        return jdbcTemplate.query("select * from testQuestion",questionRowMapper());
    }

    @Override
    public List<Question> findByQuestion(String contents) {
        return null;
    }


    @Override
    public void modifyQuestion(Question question) {}


    @Override
    public void deleteQuestion(Question question){}

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


    }
