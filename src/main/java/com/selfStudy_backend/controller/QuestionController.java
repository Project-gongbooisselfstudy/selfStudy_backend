package com.selfStudy_backend.controller;

import com.selfStudy_backend.domain.Question;
import com.selfStudy_backend.domain.UpdateQuestion;
import com.selfStudy_backend.repository.JDBCQuestionRepository;
import com.selfStudy_backend.service.QuestionServiceImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class QuestionController {

    private JdbcTemplate jdbcTemplate;
    private QuestionServiceImpl questionServiceIm;
    private Question question;
    private JDBCQuestionRepository jdbcQuestionRepository;
    private List<Question> questionSet;

    public QuestionController(JdbcTemplate jdbcTemplate, QuestionServiceImpl questionServiceIm, JDBCQuestionRepository jdbcQuestionRepository, Question question) {
        this.jdbcTemplate = jdbcTemplate;
        this.questionServiceIm = questionServiceIm;
        this.jdbcQuestionRepository = jdbcQuestionRepository;
        this.question = question;
    }


    @PostMapping(value = "question/create",   produces = "application/json; charset=UTF-8")
    public Question save(@RequestBody Question qu) {
        int count = jdbcQuestionRepository.getQuestion_id();
        String user_id = "";
        String contents = "";
        String answer = "";
        String classification = "";

        user_id = qu.getUser_id();
        contents = qu.getContents();
        answer = qu.getAnswer();
        classification = qu.getClassification();

        qu.setQuestion_id(++count);
        qu.setWrong(0);
        qu.setUser_id(user_id);
        qu.setContents(contents);
        qu.setAnswer(answer);
        qu.setClassification(classification);
        questionServiceIm.saveQuestion(qu) ;
        return qu;

    }


    // 유저가 생성한 문제만 전체 보기
    @RequestMapping(value = "question/list", produces = "application/json; charset=UTF-8")
    public List<Question> findAll(HttpServletRequest request, Model model) {
        String user_id = request.getParameter("userId");
        model.addAttribute("userId", user_id);
        return questionServiceIm.findAllQuestion(user_id);
    }

    @RequestMapping(value = "question/delete")
    public String deleteQuestion(HttpServletRequest request, Model model) {
        int question_id = Integer.parseInt(request.getParameter("questionId"));
        model.addAttribute("questionId", question_id);
        return questionServiceIm.deleteQuestion(question_id);
    }

    // TODO POSTMAN 한글 인코딩 안됨;;
    @RequestMapping(value = "question/update", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    public List<Question> updateQuestion(@RequestBody UpdateQuestion uq) {
        int question_id = uq.getQuestion_id();
        String variable = uq.getVariable();
        String updateContents = "";

        if (variable.equals("question")) {
            updateContents = uq.getUpdateContents();
        } else if (variable.equals("answer")) {
            updateContents = uq.getUpdateContents();
        } else if (variable.equals("classification")) {
            updateContents = uq.getUpdateContents();
        }
        return questionServiceIm.updateQuestion(question_id, variable, updateContents);
    }


    //랜덤으로 문제 로드하기
    //TODO 생각해보니... userID별로 랜덤으로 섞어야함. 지금은 id 상관없이 다섞었음ㅋㅋㅋ
    @RequestMapping(value="question/load")
    public List<Question> loadQuestion() {
        questionSet =  jdbcQuestionRepository.loadQuestion();
        return questionSet;
    }

    //랜덤으로 로드한 문제 풀이
    @RequestMapping(value = "question/solve", method={RequestMethod.GET})
    public String solveQuestion(HttpServletRequest request, Model model) {
        String answer = request.getParameter("answer");
        model.addAttribute("answer", answer);
        return questionServiceIm.solve(questionSet, answer);
        }

}