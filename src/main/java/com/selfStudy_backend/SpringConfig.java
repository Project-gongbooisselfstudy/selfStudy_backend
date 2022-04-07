package com.selfStudy_backend;


import com.selfStudy_backend.domain.Question;
import com.selfStudy_backend.repository.JDBCQuestionRepository;
import com.selfStudy_backend.repository.QuestionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

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

}
