package com.selfStudy_backend.controller;

import com.selfStudy_backend.domain.Question;
import com.selfStudy_backend.domain.Wrong;
import com.selfStudy_backend.repository.JDBCWrongRepository;
import com.selfStudy_backend.service.WrongServiceImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class WrongController {
    private final JdbcTemplate jdbcTemplate;
    private WrongServiceImpl wrongService;
    private Wrong wrong;

    private JDBCWrongRepository jdbcWrongRepository;
    private List<Question> wrongSet;

    public WrongController(JdbcTemplate jdbcTemplate, WrongServiceImpl wrongService, JDBCWrongRepository jdbcWrongRepository, Wrong wrong) {
        this.jdbcTemplate = jdbcTemplate;
        this.wrongService = wrongService;
        this.jdbcWrongRepository = jdbcWrongRepository;
        this.wrong = wrong;
    }

    // 유저가 생성한 문제만 전체 보기
    @RequestMapping(value = "wrong/list")
    public List<Question> findAll(HttpServletRequest request, Model model) {
        String user_id = request.getParameter("userId");
        model.addAttribute("userId", user_id);
        return wrongService.findAllQuestion(user_id);
    }

    //랜덤으로 문제 로드하기
    //TODO 생각해보니... userID별로 랜덤으로 섞어야함. 지금은 id 상관없이 다섞었음ㅋㅋㅋ
    @RequestMapping(value="wrong/load")
    public List<Question> loadWrong() {
        wrongSet =  jdbcWrongRepository.loadWrong();
        return wrongSet;
    }

    //랜덤으로 로드한 문제 풀이
    @RequestMapping(value = "wrong/solve", method={RequestMethod.GET})
    public String solveQuestion(HttpServletRequest request, Model model) {
        String answer = request.getParameter("answer");
        model.addAttribute("answer", answer);
        return wrongService.solve(wrongSet, answer);
    }


}
