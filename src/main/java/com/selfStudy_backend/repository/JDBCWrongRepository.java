package com.selfStudy_backend.repository;

import com.selfStudy_backend.domain.Question;
import com.selfStudy_backend.domain.Wrong;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JDBCWrongRepository implements WrongRepository {

    private List store = new ArrayList<>();
    private final JdbcTemplate jdbcTemplate;

    public JDBCWrongRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


//    @Override
//    public void saveWrong(Question question) {
//        String sql = "INSERT INTO testWrong(wrong_id, question_id, user_id) VALUES (?,?,?)";
//        Object[] Params = {//여기 어떻게 채우냐 (),question.getQuestion_id(), question.getUser_id()};
//        jdbcTemplate.update(sql,Params);
//    }

    @Override
    public void saveWrong(Question question) {

    }

    @Override
    public List<Wrong> findAll() {
        return jdbcTemplate.query("select * from testWrong",wrongRowMapper());
    }

    @Override
    public void validateAnswer() {

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

//    public int auto_wrong_id(){
//        String sql = "SELECT max(wrong_id) FROM testWrong;";
//        int count = jdbcTemplate.queryForObject(sql, Integer.class);
//        return count;
//    }


}
