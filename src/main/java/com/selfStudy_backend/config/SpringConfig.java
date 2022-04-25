package com.selfStudy_backend.config;


import com.selfStudy_backend.domain.Question;
import com.selfStudy_backend.domain.Wrong;
import com.selfStudy_backend.repository.JDBCQuestionRepository;
import com.selfStudy_backend.repository.JDBCWrongRepository;
import com.selfStudy_backend.repository.QuestionRepository;
import com.selfStudy_backend.repository.WrongRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;
    private  JdbcTemplate jdbcTemplate;
    public SpringConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }


//    @Bean
//    public QuestionServiceImpl questionService() {
//        JDBCQuestionRepository QuestionRepository;
//        return new QuestionServiceImpl(JDBCQuestionRepository);
//    }

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
