package com.selfStudy_backend.controller;

import com.selfStudy_backend.domain.Question;
import com.selfStudy_backend.domain.Scrap;
import com.selfStudy_backend.repository.JDBCScrapRepository;
import com.selfStudy_backend.service.ScrapServiceImple;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ScrapController {

    private final JdbcTemplate jdbcTemplate;
    private ScrapServiceImple scrapServiceImple;
    private Scrap scrap;
    private JDBCScrapRepository jdbcScrapRepository;
    private List<Scrap> scrapSet;

    public ScrapController(JdbcTemplate jdbcTemplate, ScrapServiceImple scrapServiceImple, Scrap scrap, JDBCScrapRepository jdbcScrapRepository, List<Scrap> scrapSet) {
        this.jdbcTemplate = jdbcTemplate;
        this.scrapServiceImple = scrapServiceImple;
        this.jdbcScrapRepository = jdbcScrapRepository;
        this.scrap = scrap;

    }
    //body에 googld_user에 있는 user_id 값과 testQuestion에 있는 question_id값을 넘겨주면 스크랩 테이블에 튜플이 생성되기는 하는데
    //아직 중복 방지 못했음
    @ResponseBody
    @PostMapping(value = "scrap/create", produces = "application/json; charset=UTF-8")
    public Scrap save(@RequestBody Scrap scr) {
        String user_id = "";
        int question_id = 0;

        user_id = scr.getUser_id();
        question_id = scr.getQuestion_id();

        scr.setUser_id(user_id);
        scr.setQuestion_id(question_id);

        scrapServiceImple.saveScrap(scr);
        return scr;
    }

    @RequestMapping(value = "scrap/list", produces = "application/json; charset=UTF-8")
    public List<Question> findById(HttpServletRequest request, Model model){
        String userId = request.getParameter("userId");
        model.addAttribute("userId", userId);
        return scrapServiceImple.findById(userId);
    }

    @RequestMapping(value = "scrap/delete")
    public String deleteScrap(HttpServletRequest request, Model model){
        String userId = request.getParameter("userId");
        int questionId = Integer.parseInt(request.getParameter("questionId"));
        model.addAttribute("userId", userId);
        model.addAttribute("questionId", questionId);
        return scrapServiceImple.deleteScrap(userId, questionId);
    }
}
