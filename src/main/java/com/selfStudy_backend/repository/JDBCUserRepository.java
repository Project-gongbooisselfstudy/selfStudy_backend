package com.selfStudy_backend.repository;

import com.selfStudy_backend.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JDBCUserRepository implements UserRepository{
    private final JdbcTemplate jdbcTemplate;


    public JDBCUserRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveUser(User user) {

        String sql = "INSERT INTO google_user(g_id, g_name) VALUES (?,?)";
        Object[] Params = {user.getG_id(), user.getG_name()};
        jdbcTemplate.update(sql, Params);
    }

    @Override
    public Optional<User> findById(String id) {
        List<User> result = jdbcTemplate.query("select * from google_user where g_id = ?", UserRowMapper(), id);
        return result.stream().findAny();
    }
    private RowMapper<User> UserRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setG_id(rs.getString("g_id"));
            user.setG_name(rs.getString("g_name"));


            return user;
        };
    }

}
