package com.selfStudy_backend.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.selfStudy_backend.domain.CookieUser;
import com.selfStudy_backend.domain.User;
import com.selfStudy_backend.helper.constants.SocialLoginType;
import com.selfStudy_backend.repository.JDBCQuestionRepository;
import com.selfStudy_backend.repository.JDBCUserRepository;
import com.selfStudy_backend.service.OauthServicesImple;
import lombok.extern.slf4j.Slf4j;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;


import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import java.util.Optional;


@RestController
@CrossOrigin

@RequestMapping(value = "/auth")
@Slf4j


public class OauthController {
    private OauthServicesImple oauthService;
    private JdbcTemplate jdbcTemplate;
    private User user;
    private JDBCUserRepository jdbcUserRepository;

    //지민 추가
    private JDBCQuestionRepository jdbcQuestionRepository;



    @Autowired
    public OauthController(OauthServicesImple oauthService,JdbcTemplate jdbcTemplate, User user,JDBCUserRepository jdbcUserRepository, JDBCQuestionRepository jdbcQuestionRepository ){
        this.oauthService = oauthService;
        this.jdbcTemplate = jdbcTemplate;
        this.user = user;
        this.jdbcUserRepository = jdbcUserRepository;
        //지민 추가
        this.jdbcQuestionRepository = jdbcQuestionRepository;

    }
    @RequestMapping(value="/{socialLoginType}")
    public void socialLoginType(@PathVariable(name = "socialLoginType") SocialLoginType socialLoginType, Model model)
    {
        log.info(">>사용자로부터 SNS로그인 요청을 받음 :: {} Social Login", socialLoginType);
        oauthService.request(socialLoginType);

    }

    @RequestMapping(value= "/{socialLoginType}/callback", produces = "application/json; charset=UTF-8")
    public User callback(@PathVariable(name = "socialLoginType") SocialLoginType socialLoginType, @RequestParam(name = "code") String code, HttpSession session) throws ParseException {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code :: {}", code);
        String JsonNode = oauthService.requestAccessToken(socialLoginType, code);
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(JsonNode);
        JSONObject fullToken = (JSONObject) obj;

        String accessToken = fullToken.get("access_token").toString();
        com.fasterxml.jackson.databind.JsonNode userInfo = OauthServicesImple.getGoogleUserInfo(accessToken);


        String id = userInfo.get("id").asText();
        String name = userInfo.get("name").asText();

        System.out.println(id);
        System.out.println(name);

        // 지민 추가
        CookieUser.setG_id(id);
        CookieUser.setG_name(name);
        jdbcQuestionRepository.makeRandomList();

        User user2 = new User();
        user2.setG_id(id);
        user2.setG_name(name);




        oauthService.saveUser(user2);

        return user2;

    }
}
