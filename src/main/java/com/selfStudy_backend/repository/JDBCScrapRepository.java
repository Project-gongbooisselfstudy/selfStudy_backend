package com.selfStudy_backend.repository;

import com.selfStudy_backend.domain.Question;
import com.selfStudy_backend.domain.Scrap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

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
    public List<Scrap> findById(String user_id) {
        return null;
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






}
