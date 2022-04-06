package com.selfstudy.controller;

import com.selfstudy.domain.Question;
import com.selfstudy.service.QuestionService;
import com.selfstudy.service.QuestionServiceImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.List;

@RestController
public class QuestionController {

	private JdbcTemplate jdbcTemplate;
    private QuestionServiceImpl questionServiceIm;
    private Question question;
    private int sequence = 1 ;

    public QuestionController(JdbcTemplate jdbcTemplate, QuestionServiceImpl questionServiceIm, Question question) {
        this.jdbcTemplate = jdbcTemplate;
        this.questionServiceIm = questionServiceIm;
        this.question = question;
    }


    @RequestMapping(value = "question/create", produces = "application/json; charset=UTF-8")
    public Question save() {
        Question qu = new Question();

        // 여기를 사용자가 직접 바꿀 수 있어야함


        qu.setQuestion_id(++sequence); // 이거는 자동으로 추가되게 할 수 없을까!

        qu.setWrong(0);
        qu.setUser_id("jimin");
        qu.setContents("오늘 날씨가 어때요?");
        qu.setAnswer("today is sunny~~");
        qu.setClassification("test");
        questionServiceIm.saveQuestion(qu);
        System.out.println("=========controller : 저장=========");
        return qu;
    }


    //    TODO 지금은 question/list이지만 회원의 id를 value로 입력하면 문제 리스트 전체가 보이게 하는 방법은 어떨지
    @RequestMapping(value="question/list",produces = "application/json; charset=UTF-8")
    public List<Question> findAll() {
        System.out.println("=========controller : findAll=========");
        return questionServiceIm.findAllQuestion();

    }



}