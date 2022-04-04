package com.selfstudy.repository;

import com.selfstudy.domain.Question;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import javax.sql.DataSource;
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
        String sql = "INSERT INTO testQuestion(id,question, answer, classification) VALUES (?,?,?,?)";
        Object[] Params = {question.getId(),question.getContents(), question.getAnswer(), question.getClassification()};
        jdbcTemplate.update(sql,Params);

        System.out.println("========Repository : 저장 완료========");
    }

    @Override
    public List<Question> findAll() {
        return jdbcTemplate.query("select * from question",questionRowMapper());
    }

    @Override
    public List<Question> findByQuestion(String contents) {
        return null;
    }


    @Override
    public void modifyQuestion(Question question) {}


    @Override
    public void deleteQuestion(Question question){}


    private RowMapper<Question> questionRowMapper(){
        return (rs, rowNum) -> {
            Question question = new Question();
            question.setId(rs.getInt("id"));
            question.setContents(rs.getString("question"));
            question.setAnswer(rs.getString("answer"));
            question.setClassification(rs.getString("classification"));
            return question;
        };



    }
}
