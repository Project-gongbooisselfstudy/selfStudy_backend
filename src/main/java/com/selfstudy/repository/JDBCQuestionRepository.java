package com.selfstudy.repository;

import com.selfstudy.domain.Question;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCQuestionRepository implements QuestionRepository {

    private List store = new ArrayList<>();
    private final JdbcTemplate jdbcTemplate;


    public JDBCQuestionRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveQuestion(Question question) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("testQuestion").usingGeneratedKeyColumns("id");
        Map<String , Object> parameters = new HashMap<>();

        parameters.put("question", question.getQuestion());

//        List parameters = new ArrayList();
//        parameters.add(question.getId());
//        parameters.add(question.getQuestion());
//        parameters.add(question.getAnswer());
//        parameters.add(question.getClassification());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        question.setId(key.intValue());
        System.out.println("========저장 완료========");
    }

    @Override
    public List<Question> findById(int questionId) {
        return jdbcTemplate.query("select * from question",questionRowMapper());
    }

    @Override
    public void modifyQuestion(Question question) {
    }

    private RowMapper<Question> questionRowMapper(){
        return (rs, rowNum) -> {
            Question question = new Question();
            question.setId(rs.getInt("id"));
            question.setQuestion(rs.getString("question"));
            return question;
        };



    }
}
