package com.selfStudy_backend.controller;

import com.selfStudy_backend.domain.CookieUser;
import com.selfStudy_backend.domain.Question;
import com.selfStudy_backend.domain.UpdateQuestion;
import com.selfStudy_backend.repository.JDBCQuestionRepository;
import com.selfStudy_backend.service.QuestionServiceImpl;
import org.springframework.boot.Banner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class QuestionController {
    private JdbcTemplate jdbcTemplate;
    private QuestionServiceImpl questionServiceIm;
    private Question question;
    private JDBCQuestionRepository jdbcQuestionRepository;
    private List<Question> questionSet;

    public QuestionController(JdbcTemplate jdbcTemplate, QuestionServiceImpl questionServiceIm, JDBCQuestionRepository jdbcQuestionRepository, Question question ) {
        this.jdbcTemplate = jdbcTemplate;
        this.questionServiceIm = questionServiceIm;
        this.jdbcQuestionRepository = jdbcQuestionRepository;
        this.question = question;
    }


    @PostMapping(value = "question/create",   produces = "application/json; charset=UTF-8")
    public Question save(@RequestBody Question qu, HttpServletResponse response) {
        int count = jdbcQuestionRepository.controller_getQuestion_id();
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


    // user_id 별로 생성한 문제 리스트 전체를 볼 수 있음.
    @RequestMapping(value = "question/list")
    public List<Question> findAll(HttpServletRequest request, Model model) {
        String user_id = request.getParameter("userId");
        model.addAttribute("userId", user_id);
        return questionServiceIm.findAllQuestion(user_id);
    }

    // question_id에 해당하는 문제 삭제
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
        String category = uq.getCategory();  //변경할 카테고리 (문제 / 정답 / 분류)
        String updateContents = ""; //변경할 내용

        if (category.equals("question")) {
            updateContents = uq.getUpdateContents();
        } else if (category.equals("answer")) {
            updateContents = uq.getUpdateContents();
        } else if (category.equals("classification")) {
            updateContents = uq.getUpdateContents();
        }
        return questionServiceIm.updateQuestion(question_id, category, updateContents);
    }


    //랜덤으로 문제 로드하기
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