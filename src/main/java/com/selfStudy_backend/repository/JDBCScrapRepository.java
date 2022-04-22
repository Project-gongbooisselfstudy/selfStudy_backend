package com.selfStudy_backend.repository;

import com.selfStudy_backend.domain.Question;
import com.selfStudy_backend.domain.Scrap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JDBCScrapRepository implements ScrapRepository{

    private final JdbcTemplate jdbcTemplate;


    public JDBCScrapRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);

    }
    //스크랩 저장
    @Override
    public void saveScrap(Scrap scrap) {
        String sql = "INSERT INTO Scrap(user_id, question_id) VALUES (?,?)";
        Object[] Params = {scrap.getUser_id(), scrap.getQuestion_id()};
        jdbcTemplate.update(sql, Params);
    }
    //id별 스크랩 조회
    @Override
    public List<Question> findById(String user_id) {
        String sql = "select * from testQuestion where question_id in (select question_id from Scrap where Scrap.user_id = ?)";
        List<Question> result = jdbcTemplate.query(sql, questionRowMapper(), user_id);
        return result;
    }

    @Override
    public Optional<Scrap> findByUserIdAndQuestionId(String user_id, int question_id){
        String sql = "select * from Scrap where user_id = ? and question_id = ?";
        List<Scrap> result = jdbcTemplate.query(sql, scrapRowMapper(), user_id, question_id);
        return result.stream().findAny();
    }
    //스크랩 삭제
    @Override
    public String deleteScrap(String user_id, int question_id){
        String sql = "delete from Scrap where user_id = ? and question_id = ?";
        jdbcTemplate.update(sql,user_id, question_id);
        return "스크랩 취소";
    }
    //mapper
    private RowMapper<Scrap> scrapRowMapper() {
        return (rs, rowNum) -> {
            Scrap scrap = new Scrap();
            scrap.setUser_id(rs.getString("user_id"));
            scrap.setQuestion_id(rs.getInt("question_id"));

            return scrap;
        };
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






}
