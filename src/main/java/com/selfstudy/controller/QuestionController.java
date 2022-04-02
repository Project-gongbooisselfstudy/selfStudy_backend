package com.selfstudy.controller;

import com.selfstudy.domain.Question;
import com.selfstudy.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.sql.DataSource;

@Controller
public class QuestionController {


	private JdbcTemplate jdbcTemplate;

	public QuestionController(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
//    @Autowired
//    private final QuestionService questionService;
//    private final Question question;
//
//    public QuestionController(QuestionService questionService, Question question) {
//        this.questionService = questionService;
//        this.question = question;
//    }

    // private String question;
    // private String answer;
    // private String classification;

    @GetMapping("create")
    public String createForm(@RequestParam String question ,@RequestParam String answer ,@RequestParam String classification){

        System.out.println("===값 전달받기 === ");
        String sql = "INSERT INTO testQuestion(question, answer, classification) VALUES (?,?,?)";
        Object[] Params = {question, answer, classification};

        jdbcTemplate.update(sql,Params);

        System.out.println("=======된건가..?=======");
        return "questionForm";
    }



}