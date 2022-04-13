package com.selfstudy.controller;

import com.selfstudy.domain.Question;
import com.selfstudy.repository.JDBCQuestionRepository;
import com.selfstudy.service.QuestionService;
import com.selfstudy.service.QuestionServiceImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@RestController
public class QuestionController {

	private final JdbcTemplate jdbcTemplate;
    private QuestionServiceImpl questionServiceIm;
    private Question question;
    private JDBCQuestionRepository jdbcQuestionRepository;




    public QuestionController(JdbcTemplate jdbcTemplate, QuestionServiceImpl questionServiceIm, JDBCQuestionRepository jdbcQuestionRepository,  Question question) {
        this.jdbcTemplate = jdbcTemplate;
        this.questionServiceIm = questionServiceIm;
        this.jdbcQuestionRepository = jdbcQuestionRepository;
        this.question = question;
    }


    @RequestMapping(value = "question/create", produces = "application/json; charset=UTF-8")
    public Question save() {
        Question qu = new Question();
        int count =jdbcQuestionRepository.getQuestion_id();

        // TODO 여기를 사용자가 직접 바꿀 수 있어야함
        qu.setQuestion_id(++count);
        qu.setWrong(0);
        qu.setUser_id("user3");
        qu.setContents("정답은?");
        qu.setAnswer("4번");
        qu.setClassification("test");
        questionServiceIm.saveQuestion(qu);
        return qu;
    }


    //    TODO 지금은 question/list이지만 회원의 id를 value로 입력하면 문제 리스트 전체가 보이게 하는 방법은 어떨지
    @RequestMapping(value="question/list",produces = "application/json; charset=UTF-8")
    public List<Question> findAll() {
        return questionServiceIm.findAllQuestion();

    }

    @RequestMapping(value="question/userQuestion")
    public List<Question> findById(HttpServletRequest request , Model model){
        String user_id = request.getParameter("userId");
        model.addAttribute("userId", user_id);
        return questionServiceIm.findById(user_id);
    }

    @RequestMapping(value="question/findQuestion")
    public Optional<Question> findByQuestion(HttpServletRequest request , Model model){
        int question_id = Integer.parseInt(request.getParameter("question_id"));
        model.addAttribute("question_id", question_id);
        return questionServiceIm.findByQuestion(question_id);
    }


    @RequestMapping(value= "question/delete")
    public String deleteQuestion(HttpServletRequest request , Model model){
        int question_id = Integer.parseInt(request.getParameter("question_id"));
        model.addAttribute("question_id", question_id);
        return questionServiceIm.deleteQuestion(question_id);
    }

    @RequestMapping(value = "question/randomNext")
    public List<Question> randomQuestionNext(){
        return questionServiceIm.randomQuestionNext();
    }

    @RequestMapping(value = "question/randomPrev")
    public List<Question> randomQuestionPrev(){
        return questionServiceIm.randomQuestionPrev();
    }

}