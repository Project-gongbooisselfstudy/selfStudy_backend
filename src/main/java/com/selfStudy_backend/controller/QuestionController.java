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
import java.util.Optional;

@RestController
public class QuestionController {

    private final JdbcTemplate jdbcTemplate;
    private QuestionServiceImpl questionServiceIm;
    private Question question;
    private JDBCQuestionRepository jdbcQuestionRepository;


    public QuestionController(JdbcTemplate jdbcTemplate, QuestionServiceImpl questionServiceIm, JDBCQuestionRepository jdbcQuestionRepository, Question question) {
        this.jdbcTemplate = jdbcTemplate;
        this.questionServiceIm = questionServiceIm;
        this.jdbcQuestionRepository = jdbcQuestionRepository;
        this.question = question;
    }


        @RequestMapping(value = "question/create", produces = "application/json; charset=UTF-8")

//    @RequestMapping(value = "MakeProblem", produces = "application/json; charset=UTF-8")
    public Question save() {
        Question qu = new Question();
        int count = jdbcQuestionRepository.getQuestion_id();

        // TODO 여기를 사용자가 직접 바꿀 수 있어야함
        qu.setQuestion_id(++count);
        qu.setWrong(0);
        qu.setUser_id("new");
        qu.setContents("spring");
        qu.setAnswer("스프링");
        qu.setClassification("단어장");
        questionServiceIm.saveQuestion(qu);
        return qu;
    }


    //    TODO 지금은 question/list이지만 회원의 id를 value로 입력하면 문제 리스트 전체가 보이게 하는 방법은 어떨지
    @RequestMapping(value = "question/list", produces = "application/json; charset=UTF-8")
    public List<Question> findAll() {
        return questionServiceIm.findAllQuestion();

    }

    @RequestMapping(value = "question/userQuestion")
    public List<Question> findById(HttpServletRequest request, Model model) {
        String user_id = request.getParameter("userId");
        model.addAttribute("userId", user_id);
        return questionServiceIm.findById(user_id);
    }

    @RequestMapping(value = "question/findQuestion")
    public Optional<Question> findByQuestion(HttpServletRequest request, Model model) {
        int question_id = Integer.parseInt(request.getParameter("questionId"));
        model.addAttribute("questionId", question_id);
        return questionServiceIm.findByQuestion(question_id);
    }


    @RequestMapping(value = "question/delete")
    public String deleteQuestion(HttpServletRequest request, Model model) {
        int question_id = Integer.parseInt(request.getParameter("questionId"));
        model.addAttribute("questionId", question_id);
        return questionServiceIm.deleteQuestion(question_id);
    }

//    @RequestMapping(value = "question/randomNext")
//    public List<Question> randomQuestionNext() {
//        return questionServiceIm.randomQuestionNext();
//    }

    // 위에 randomNext의 문제를 보고 답을 answer로 보냄
    @RequestMapping(value = "question/randomNext", method={RequestMethod.GET, RequestMethod.POST})
    public List<Question> solveQuestion(HttpServletRequest request, Model model) {

        if (request.getMethod().equals("GET")) {
        String answer = request.getParameter("answer");
        model.addAttribute("answer", answer);
//        questionServiceIm.solve(answer);
        return questionServiceIm.solve(answer);
        }

        return questionServiceIm.randomQuestionNext();
    }

    @RequestMapping(value = "question/randomPrev")
    public List<Question> randomQuestionPrev() {
        return questionServiceIm.randomQuestionPrev();
    }


    @RequestMapping(value = "question/update", method = RequestMethod.POST)
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



}