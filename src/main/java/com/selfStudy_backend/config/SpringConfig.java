package com.selfStudy_backend.config;


import com.selfStudy_backend.domain.CookieUser;
import com.selfStudy_backend.domain.Question;
import com.selfStudy_backend.domain.Wrong;
import com.selfStudy_backend.repository.JDBCQuestionRepository;
import com.selfStudy_backend.repository.JDBCWrongRepository;
import com.selfStudy_backend.repository.QuestionRepository;
import com.selfStudy_backend.repository.WrongRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;
    private  JdbcTemplate jdbcTemplate;
    private static CookieUser cookieUser;
    public SpringConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }


    @Bean
    public QuestionRepository questionRepository() {
        return new JDBCQuestionRepository(dataSource);
    }

    @Bean
    Question question() {
        return new Question();
    }

    @Bean
    Wrong wrong() {return new Wrong();}

    @Bean
    public WrongRepository wrongRepository() {
        return new JDBCWrongRepository(dataSource);
    }

}
