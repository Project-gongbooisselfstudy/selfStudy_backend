package com.selfstudy.controller;

import com.selfstudy.domain.Question;
import com.selfstudy.service.QuestionService;
import com.selfstudy.service.QuestionServiceImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;

@RestController
public class QuestionController {

	private JdbcTemplate jdbcTemplate;
    private QuestionServiceImpl questionServiceIm;
    private Question question;

    public QuestionController(JdbcTemplate jdbcTemplate, QuestionServiceImpl questionServiceIm, Question question) {
        this.jdbcTemplate = jdbcTemplate;
        this.questionServiceIm = questionServiceIm;
        this.question = question;
    }

    @RequestMapping(value = "question/create", produces = "application/json; charset=UTF-8")
    public Question save() {
        Question qu = new Question();
        qu.setId(2);
        qu.setContents("test이지롱");
        qu.setAnswer("db이지롱");
        qu.setClassification("test");
        questionServiceIm.saveQuestion(qu);
        System.out.println("=========controller : 저장=========");
        return qu;
    }



}