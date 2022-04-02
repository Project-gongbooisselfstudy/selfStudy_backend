package com.selfstudy;


import com.selfstudy.domain.Question;
import com.selfstudy.repository.JDBCQuestionRepository;
import com.selfstudy.repository.QuestionRepository;
import com.selfstudy.service.QuestionService;
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


    @Bean
    public QuestionService questionService() {
        return new QuestionService(questionRepository());
    }

    @Bean
    public QuestionRepository questionRepository() {
        return new JDBCQuestionRepository(dataSource);
    }

    @Bean Question question(){
        return new Question();


    }

}
